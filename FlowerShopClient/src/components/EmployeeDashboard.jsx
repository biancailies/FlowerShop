import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { INVENTORY_API } from '../api/api';
import FlowerCard from './FlowerCard';
import { translations } from '../translations/translations';
import { exportToCSV, exportToJSON, exportToXML, exportToDOC } from '../utils/exportUtils';

const EmployeeDashboard = ({ lang, user }) => {
    const t = translations[lang];
    const [allStocks, setAllStocks] = useState([]);
    const [stocks, setStocks] = useState([]);
    
    // Filters & Search
    const [search, setSearch] = useState('');
    const [filterColor, setFilterColor] = useState('');
    const [minPrice, setMinPrice] = useState('');
    const [maxPrice, setMaxPrice] = useState('');
    const [minQuantity, setMinQuantity] = useState('');
    const [sortBy, setSortBy] = useState('none');
    
    // Stock Form
    const [stockForm, setStockForm] = useState({ stockId: '', flowerId: '', color: '', quantity: '' });
    const [isEditMode, setIsEditMode] = useState(false);

    const fetchStocks = async () => {
        try {
            const res = await axios.get(`${INVENTORY_API}/expanded`);
            setAllStocks(res.data);
            const employeeStocks = res.data.filter(s => s.flowerShopId === user.flowerShopId);
            setStocks(employeeStocks);
        } catch (error) {
            console.error("Failed to fetch employee stocks", error);
        }
    };

    useEffect(() => {
        fetchStocks();
    }, []);

    const handleSell = async (stockId) => {
        const qtyToSell = prompt("How many to sell?", "1");
        if (!qtyToSell || isNaN(qtyToSell) || qtyToSell <= 0) return;

        try {
            await axios.put(`${INVENTORY_API}/sell/${stockId}/${qtyToSell}`);
            fetchStocks();
            alert("Sale successful!");
        } catch (error) {
            alert(t.notEnoughStock || "Sell failed. Out of stock or error.");
        }
    };

    const handleStockSubmit = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                id: isEditMode ? { stockId: stockForm.stockId } : null,
                flowerId: parseInt(stockForm.flowerId),
                flowerShopId: user.flowerShopId,
                color: stockForm.color,
                quantity: parseInt(stockForm.quantity)
            };

            if (isEditMode) {
                await axios.put(INVENTORY_API, payload);
                alert(t.updateSuccess || "Stock updated!");
            } else {
                await axios.post(INVENTORY_API, payload);
                alert(t.createSuccess || "Stock added!");
            }
            setStockForm({ stockId: '', flowerId: '', color: '', quantity: '' });
            setIsEditMode(false);
            fetchStocks();
        } catch (error) {
            console.error(error);
            alert("Error saving stock.");
        }
    };

    const editStock = (s) => {
        setIsEditMode(true);
        setStockForm({ stockId: s.stockId, flowerId: s.flowerId, color: s.color, quantity: s.quantity });
    };

    const cancelEdit = () => {
        setIsEditMode(false);
        setStockForm({ stockId: '', flowerId: '', color: '', quantity: '' });
    };

    // Exports
    const exportData = (type) => {
        const filename = `Employee_Shop_${user.flowerShopId}_Stocks`;
        if (type === 'CSV') exportToCSV(filename, stocks);
        if (type === 'JSON') exportToJSON(filename, stocks);
        if (type === 'XML') exportToXML(filename, stocks, 'stocks', 'stock');
        if (type === 'DOC') exportToDOC(filename, "Employee Shop Stocks", stocks);
    };

    // Fallback Search Logic
    if (search.trim() !== '') {
        const matchingLocal = stocks.filter(s => s.flowerName.toLowerCase().includes(search.toLowerCase()));
        
        if (matchingLocal.length > 0) {
            return (
                <div className="page-container">
                    <div className="filters-bar" style={{ justifyContent: 'center' }}>
                        <input type="text" placeholder={t.searchPlaceholder} value={search} onChange={(e) => setSearch(e.target.value)} className="search-input" />
                    </div>
                    <div className="flowers-grid">
                        {matchingLocal.map(flower => (
                            <FlowerCard key={flower.stockId} flower={flower} lang={lang} onSell={handleSell} showSell={true} />
                        ))}
                    </div>
                </div>
            );
        } else {
            // Fallback to global
            const matchingGlobal = allStocks.filter(s => s.flowerName.toLowerCase().includes(search.toLowerCase()) && s.quantity > 0);
            const grouped = {};
            matchingGlobal.forEach(s => {
                if (!grouped[s.flowerName]) grouped[s.flowerName] = {};
                if (!grouped[s.flowerName][s.flowerShopName]) grouped[s.flowerName][s.flowerShopName] = [];
                grouped[s.flowerName][s.flowerShopName].push(s.color);
            });

            return (
                <div className="page-container">
                    <div className="filters-bar" style={{ justifyContent: 'center' }}>
                        <input type="text" placeholder={t.searchPlaceholder} value={search} onChange={(e) => setSearch(e.target.value)} className="search-input" />
                    </div>
                    <div className="search-results">
                        <h4 style={{color: 'red'}}>Not found locally. Available elsewhere:</h4>
                        {Object.keys(grouped).length === 0 ? <p>{t.noFlowers}</p> : (
                            Object.keys(grouped).map(flowerName => (
                                <div key={flowerName} className="grouped-flower-result">
                                    <h3>{flowerName}</h3>
                                    <ul>
                                        {Object.keys(grouped[flowerName]).map(shopName => (
                                            <li key={shopName}><strong>{shopName}:</strong> {grouped[flowerName][shopName].join(', ')}</li>
                                        ))}
                                    </ul>
                                </div>
                            ))
                        )}
                    </div>
                </div>
            );
        }
    }

    // Normal view
    const uniqueColors = [...new Set(stocks.map(s => s.color.toLowerCase()))].sort();

    let displayedStocks = stocks;
    if (filterColor) displayedStocks = displayedStocks.filter(s => s.color.toLowerCase() === filterColor);
    if (minPrice) displayedStocks = displayedStocks.filter(s => s.sellingPrice >= parseFloat(minPrice));
    if (maxPrice) displayedStocks = displayedStocks.filter(s => s.sellingPrice <= parseFloat(maxPrice));
    if (minQuantity) displayedStocks = displayedStocks.filter(s => s.quantity >= parseInt(minQuantity));

    if (sortBy === 'price_asc') displayedStocks.sort((a, b) => a.sellingPrice - b.sellingPrice);
    if (sortBy === 'price_desc') displayedStocks.sort((a, b) => b.sellingPrice - a.sellingPrice);

    return (
        <div className="page-container">
            <div className="dashboard-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2>🧑‍🌾 {t.employeeDashboard} (Shop ID: {user.flowerShopId})</h2>
                <div>
                    <button className="btn" style={{marginRight:'5px'}} onClick={() => exportData('CSV')}>{t.exportCSV || "CSV"}</button>
                    <button className="btn" style={{marginRight:'5px'}} onClick={() => exportData('JSON')}>{t.exportJSON || "JSON"}</button>
                    <button className="btn" style={{marginRight:'5px'}} onClick={() => exportData('XML')}>{t.exportXML || "XML"}</button>
                    <button className="btn" onClick={() => exportData('DOC')}>{t.exportDOC || "DOC"}</button>
                </div>
            </div>
            
            <div className="crud-section" style={{ marginTop: '20px' }}>
                <h3>{isEditMode ? t.edit || "Edit" : t.add || "Add"} {t.stock}</h3>
                <form onSubmit={handleStockSubmit} className="crud-form">
                    <input type="number" placeholder="Flower ID" value={stockForm.flowerId} onChange={e => setStockForm({...stockForm, flowerId: e.target.value})} required disabled={isEditMode} title={isEditMode ? "Cannot change Flower ID" : ""} />
                    <input type="text" placeholder={t.color} value={stockForm.color} onChange={e => setStockForm({...stockForm, color: e.target.value})} required />
                    <input type="number" placeholder={t.stock} value={stockForm.quantity} onChange={e => setStockForm({...stockForm, quantity: e.target.value})} required min="0" />
                    <button type="submit" className="btn btn-primary">{t.save}</button>
                    {isEditMode && <button type="button" className="btn" onClick={cancelEdit}>{t.cancel || "Cancel"}</button>}
                </form>
            </div>

            <div className="filters-bar" style={{ flexWrap: 'wrap' }}>
                <input type="text" placeholder={t.searchPlaceholder} value={search} onChange={(e) => setSearch(e.target.value)} className="search-input" />
                <select value={filterColor} onChange={(e) => setFilterColor(e.target.value)} className="search-input" style={{width: '150px'}}>
                    <option value="">Toate culorile</option>
                    {uniqueColors.map(c => (
                        <option key={c} value={c}>{c.charAt(0).toUpperCase() + c.slice(1)}</option>
                    ))}
                </select>
                <input type="number" placeholder="Min Price" value={minPrice} onChange={(e) => setMinPrice(e.target.value)} className="search-input" style={{width: '100px'}} />
                <input type="number" placeholder="Max Price" value={maxPrice} onChange={(e) => setMaxPrice(e.target.value)} className="search-input" style={{width: '100px'}} />
                <input type="number" placeholder={t.minQuantity} value={minQuantity} onChange={(e) => setMinQuantity(e.target.value)} className="search-input" style={{width: '120px'}} />
                
                <div className="sort-controls" style={{ display: 'flex', alignItems: 'center', gap: '5px' }}>
                    <label style={{ fontSize: '0.9rem' }}>{t.sortBy}: </label>
                    <select value={sortBy} onChange={(e) => setSortBy(e.target.value)} className="search-input" style={{ width: '130px', margin: 0 }}>
                        <option value="none">--</option>
                        <option value="price_asc">{t.price} ASC</option>
                        <option value="price_desc">{t.price} DESC</option>
                    </select>
                </div>
            </div>

            <div className="flowers-grid">
                {displayedStocks.length > 0 ? (
                    displayedStocks.map(flower => (
                        <div key={flower.stockId} style={{position: 'relative'}}>
                            <FlowerCard flower={flower} lang={lang} onSell={handleSell} showSell={true} />
                            <button className="btn btn-primary" style={{position: 'absolute', top: '10px', right: '10px'}} onClick={() => editStock(flower)}>{t.edit || "Edit"}</button>
                        </div>
                    ))
                ) : (
                    <p>{t.noFlowers}</p>
                )}
            </div>
        </div>
    );
};

export default EmployeeDashboard;
