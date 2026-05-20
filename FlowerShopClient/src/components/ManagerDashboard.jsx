import React, { useState, useEffect } from 'react';
import apiClient, { FLOWER_API, IMAGE_API } from '../api/api';
import { translations } from '../translations/translations';
import StatisticsPage from './StatisticsPage';
import PublicFlowers from './PublicFlowers';
import { exportToCSV, exportToJSON, exportToXML, exportToDOC } from '../utils/exportUtils';

const ManagerDashboard = ({ lang }) => {
    const t = translations[lang];
    const [flowers, setFlowers] = useState([]);
    const [activeTab, setActiveTab] = useState('catalog');
    
    const [formData, setFormData] = useState({ id: { flowerId: '' }, name: '', purchasePrice: '', sellingPrice: '' });
    const [isEditMode, setIsEditMode] = useState(false);

    const [images, setImages] = useState([]);
    const [imageForm, setImageForm] = useState({ id: { id: '' }, flowerId: { flowerId: '' }, url: '' });
    const [isImageEdit, setIsImageEdit] = useState(false);

    const fetchFlowers = async () => {
        try {
            const res = await apiClient.get(FLOWER_API);
            setFlowers(res.data);
        } catch (err) { console.error(err); }
    };

    const fetchImages = async () => {
        try {
            const res = await apiClient.get(IMAGE_API);
            setImages(res.data);
        } catch (err) { console.error(err); }
    };

    useEffect(() => { 
        fetchFlowers(); 
        fetchImages();
    }, []);

    const handleFlowerSubmit = async (e) => {
        e.preventDefault();
        try {
            if (isEditMode) {
                await apiClient.put(FLOWER_API, formData);
                alert(t.updateSuccess || "Flower updated");
            } else {
                await apiClient.post(FLOWER_API, {
                    name: formData.name,
                    purchasePrice: parseFloat(formData.purchasePrice),
                    sellingPrice: parseFloat(formData.sellingPrice)
                });
                alert(t.createSuccess || "Flower created");
            }
            fetchFlowers();
            setFormData({ id: { flowerId: '' }, name: '', purchasePrice: '', sellingPrice: '' });
            setIsEditMode(false);
        } catch (err) { console.error(err); }
    };

    const editFlower = (f) => {
        setIsEditMode(true);
        setFormData(f);
    };

    const handleFlowerDelete = async (flowerId) => {
        try {
            await apiClient.delete(`${FLOWER_API}/${flowerId}`);
            fetchFlowers();
        } catch (err) { console.error(err); }
    };

    const handleImageFileChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setImageForm({ ...imageForm, url: reader.result });
            };
            reader.readAsDataURL(file);
        }
    };

    const handleImageSubmit = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                id: isImageEdit ? imageForm.id : null,
                flowerId: { flowerId: parseInt(imageForm.flowerId.flowerId) },
                url: imageForm.url
            };
            if (isImageEdit) {
                await apiClient.put(IMAGE_API, payload);
                alert("Image updated");
            } else {
                await apiClient.post(IMAGE_API, payload);
                alert("Image added");
            }
            fetchImages();
            setImageForm({ id: { id: '' }, flowerId: { flowerId: '' }, url: '' });
            setIsImageEdit(false);
            
            // Reset file input
            const fileInput = document.getElementById('imageFileInput');
            if (fileInput) fileInput.value = '';
        } catch (err) { console.error(err); }
    };

    const editImage = (img) => {
        setIsImageEdit(true);
        setImageForm(img);
    };

    const handleImageDelete = async (id) => {
        try {
            await apiClient.delete(`${IMAGE_API}/${id}`);
            fetchImages();
        } catch (err) { console.error(err); }
    };

    const exportData = (type) => {
        const filename = `Manager_Flowers_Catalog`;
        if (type === 'CSV') exportToCSV(filename, flowers);
        if (type === 'JSON') exportToJSON(filename, flowers);
        if (type === 'XML') exportToXML(filename, flowers, 'flowers', 'flower');
        if (type === 'DOC') exportToDOC(filename, "Flower Catalog", flowers);
    };

    return (
        <div className="page-container">
            <div className="dashboard-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '10px' }}>
                <h2>📈 {t.managerDashboard}</h2>
                <div style={{ display: 'flex', gap: '5px' }}>
                    <button className="btn" onClick={() => exportData('CSV')}>{t.exportCSV || "CSV"}</button>
                    <button className="btn" onClick={() => exportData('JSON')}>{t.exportJSON || "JSON"}</button>
                    <button className="btn" onClick={() => exportData('XML')}>{t.exportXML || "XML"}</button>
                    <button className="btn" onClick={() => exportData('DOC')}>{t.exportDOC || "DOC"}</button>
                </div>
            </div>

            <div className="tab-buttons" style={{ display: 'flex', gap: '10px', marginTop: '15px', marginBottom: '25px', flexWrap: 'wrap' }}>
                <button 
                    className={`btn ${activeTab === 'catalog' ? 'btn-primary' : ''}`} 
                    onClick={() => setActiveTab('catalog')}
                    style={{ background: activeTab === 'catalog' ? '#ff8da1' : '#f0f0f0', color: activeTab === 'catalog' ? '#fff' : '#333' }}
                >
                    🌸 Vizualizare Catalog (Client View)
                </button>
                <button 
                    className={`btn ${activeTab === 'crud' ? 'btn-primary' : ''}`} 
                    onClick={() => setActiveTab('crud')}
                    style={{ background: activeTab === 'crud' ? '#ff8da1' : '#f0f0f0', color: activeTab === 'crud' ? '#fff' : '#333' }}
                >
                    🛠 Administrare Catalog (CRUD)
                </button>
                <button 
                    className={`btn ${activeTab === 'stats' ? 'btn-primary' : ''}`} 
                    onClick={() => setActiveTab('stats')}
                    style={{ background: activeTab === 'stats' ? '#ff8da1' : '#f0f0f0', color: activeTab === 'stats' ? '#fff' : '#333' }}
                >
                    📈 Statistici Vânzări
                </button>
            </div>

            {activeTab === 'catalog' && (
                <div style={{ marginTop: '10px' }}>
                    <PublicFlowers lang={lang} user={{ type: 'MANAGER' }} />
                </div>
            )}

            {activeTab === 'stats' && (
                <div style={{ marginTop: '10px' }}>
                    <StatisticsPage lang={lang} />
                </div>
            )}

            {activeTab === 'crud' && (
                <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap' }}>
                    <div className="crud-section" style={{ flex: 1, minWidth: '300px' }}>
                        <h3>{isEditMode ? t.edit || "Edit" : t.add || "Add"} Flower</h3>
                        <form onSubmit={handleFlowerSubmit} className="crud-form">
                            <input type="text" placeholder={t.name} value={formData.name} onChange={e => setFormData({...formData, name: e.target.value})} required />
                            <input type="number" placeholder={t.purchasePrice} value={formData.purchasePrice} onChange={e => setFormData({...formData, purchasePrice: e.target.value})} required step="0.01" />
                            <input type="number" placeholder={t.sellingPrice} value={formData.sellingPrice} onChange={e => setFormData({...formData, sellingPrice: e.target.value})} required step="0.01" />
                            <button type="submit" className="btn btn-primary">{t.save}</button>
                            {isEditMode && <button type="button" className="btn" onClick={() => { setIsEditMode(false); setFormData({ id: { flowerId: '' }, name: '', purchasePrice: '', sellingPrice: '' }); }}>Cancel</button>}
                        </form>

                        <table className="data-table" style={{ marginTop: '20px' }}>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>{t.name}</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {flowers.map(f => (
                                    <tr key={f.id.flowerId}>
                                        <td>{f.id.flowerId}</td>
                                        <td>{f.name}</td>
                                        <td>
                                            <button className="btn btn-secondary" onClick={() => editFlower(f)}>{t.edit || "Edit"}</button>
                                            <button className="btn btn-danger" onClick={() => handleFlowerDelete(f.id.flowerId)}>{t.delete}</button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    <div className="crud-section" style={{ flex: 1, minWidth: '300px' }}>
                        <h3>{isImageEdit ? "Edit Image" : "Add Image"}</h3>
                        <form onSubmit={handleImageSubmit} className="crud-form">
                            <input type="number" placeholder="Flower ID" value={imageForm.flowerId.flowerId} onChange={e => setImageForm({...imageForm, flowerId: { flowerId: e.target.value }})} required />
                            
                            <input 
                                type="file" 
                                id="imageFileInput"
                                accept="image/*" 
                                onChange={handleImageFileChange} 
                                required={!isImageEdit && !imageForm.url} 
                            />
                            <input 
                                type="hidden" 
                                value={imageForm.url} 
                                required 
                            />
                            
                            {imageForm.url && <img src={imageForm.url} alt="Preview" style={{width: '100px', height: '100px', objectFit: 'cover', borderRadius: '8px'}} />}
                            <button type="submit" className="btn btn-primary">{t.save}</button>
                            {isImageEdit && <button type="button" className="btn" onClick={() => { setIsImageEdit(false); setImageForm({ id: { id: '' }, flowerId: { flowerId: '' }, url: '' }); }}>Cancel</button>}
                        </form>

                        <table className="data-table" style={{ marginTop: '20px' }}>
                            <thead>
                                <tr>
                                    <th>Img ID</th>
                                    <th>Flower ID</th>
                                    <th>Preview</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {images.map(img => (
                                    <tr key={img.id.id}>
                                        <td>{img.id.id}</td>
                                        <td>{img.flowerId.flowerId}</td>
                                        <td><img src={img.url} alt="Preview" style={{width: '40px', height: '40px', objectFit: 'cover', borderRadius: '4px'}} /></td>
                                        <td>
                                            <button className="btn btn-secondary" onClick={() => editImage(img)}>{t.edit || "Edit"}</button>
                                            <button className="btn btn-danger" onClick={() => handleImageDelete(img.id.id)}>{t.delete}</button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ManagerDashboard;
