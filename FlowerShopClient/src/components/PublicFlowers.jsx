import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { INVENTORY_API, FLOWERSHOP_API } from '../api/api';
import FlowerCard from './FlowerCard';
import { translations } from '../translations/translations';

const PublicFlowers = ({ lang, user }) => {
    const t = translations[lang];
    const [stocks, setStocks] = useState([]);
    const [shops, setShops] = useState([]);
    const [selectedShopId, setSelectedShopId] = useState('ALL');
    const [search, setSearch] = useState('');
    const [filterColor, setFilterColor] = useState('');
    const [sortBy, setSortBy] = useState('none');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [stocksRes, shopsRes] = await Promise.all([
                    axios.get(`${INVENTORY_API}/expanded`),
                    axios.get(FLOWERSHOP_API)
                ]);
                // Only keep positive stock
                setStocks(stocksRes.data.filter(s => s.quantity > 0));
                setShops(shopsRes.data);
            } catch (err) {
                console.error("Failed to fetch data", err);
            }
        };
        fetchData();
    }, []);

    let displayedStocks = stocks;
    const uniqueColors = [...new Set(stocks.map(s => s.color.toLowerCase()))].sort();

    // Search Mode (Global grouping)
    if (search.trim() !== '') {
        displayedStocks = displayedStocks.filter(s =>
            s.flowerName.toLowerCase().includes(search.toLowerCase())
        );

        // Group by flower name -> shop -> colors
        const grouped = {};
        displayedStocks.forEach(s => {
            if (!grouped[s.flowerName]) grouped[s.flowerName] = {};
            if (!grouped[s.flowerName][s.flowerShopName]) grouped[s.flowerName][s.flowerShopName] = [];
            grouped[s.flowerName][s.flowerShopName].push(s.color);
        });

        return (
            <div className="page-container">
                <div className="filters-bar" style={{ justifyContent: 'center' }}>
                    <input
                        type="text"
                        placeholder={t.searchPlaceholder}
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        className="search-input"
                        style={{ width: '400px' }}
                    />
                </div>
                <div className="search-results">
                    {Object.keys(grouped).length === 0 ? (
                        <p>{t.noFlowers}</p>
                    ) : (
                        Object.keys(grouped).map(flowerName => (
                            <div key={flowerName} className="grouped-flower-result">
                                <h3>{flowerName}</h3>
                                <ul>
                                    {Object.keys(grouped[flowerName]).map(shopName => (
                                        <li key={shopName}>
                                            <strong>{shopName}:</strong> {grouped[flowerName][shopName].join(', ')}
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        ))
                    )}
                </div>
            </div>
        );
    }

    // Normal Catalog Mode
    if (selectedShopId !== 'ALL') {
        displayedStocks = displayedStocks.filter(s => s.flowerShopId === parseInt(selectedShopId));
    }

    if (filterColor !== '') {
        displayedStocks = displayedStocks.filter(s => s.color.toLowerCase() === filterColor);
    }

    if (sortBy === 'price_asc') displayedStocks.sort((a, b) => a.sellingPrice - b.sellingPrice);
    if (sortBy === 'price_desc') displayedStocks.sort((a, b) => b.sellingPrice - a.sellingPrice);

    return (
        <div className="page-container">
            {user && user.type === 'CLIENT' && (
                <div className="client-banner">
                    <h3>🌸 {t.loggedInAs} Client - {t.publicView}</h3>
                </div>
            )}

            <div className="filters-bar">
                <select value={selectedShopId} onChange={(e) => setSelectedShopId(e.target.value)} className="shop-select">
                    <option value="ALL">{t.allShops}</option>
                    {shops.map(shop => (
                        <option key={shop.id.flowerShopId} value={shop.id.flowerShopId}>{shop.name}</option>
                    ))}
                </select>

                <select value={filterColor} onChange={(e) => setFilterColor(e.target.value)} className="shop-select" style={{ marginLeft: '10px' }}>
                    <option value="">Toate culorile</option>
                    {uniqueColors.map(c => (
                        <option key={c} value={c}>{c.charAt(0).toUpperCase() + c.slice(1)}</option>
                    ))}
                </select>

                <input
                    type="text"
                    placeholder={t.searchPlaceholder}
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    className="search-input"
                />

                <div className="sort-controls">
                    <label>{t.sortBy}: </label>
                    <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
                        <option value="none">--</option>
                        <option value="price_asc">{t.price} ASC</option>
                        <option value="price_desc">{t.price} DESC</option>
                    </select>
                </div>
            </div>

            <div className="flowers-grid">
                {displayedStocks.length > 0 ? (
                    displayedStocks.map(flower => (
                        <FlowerCard
                            key={flower.stockId}
                            flower={flower}
                            lang={lang}
                            showSell={false}
                        />
                    ))
                ) : (
                    <p>{t.noFlowers}</p>
                )}
            </div>
        </div>
    );
};

export default PublicFlowers;
