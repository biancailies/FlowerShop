import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { USER_API } from '../api/api';
import { translations } from '../translations/translations';
import NotificationsPage from './NotificationsPage';
import { exportToCSV, exportToJSON, exportToXML, exportToDOC } from '../utils/exportUtils';

const AdminDashboard = ({ lang }) => {
    const t = translations[lang];
    const [users, setUsers] = useState([]);
    const [viewNotifs, setViewNotifs] = useState(false);
    const [formData, setFormData] = useState({ username: '', password: '', type: 'CLIENT', flowerShopId: '' });
    const [filterType, setFilterType] = useState('ALL');
    const [editMode, setEditMode] = useState(false);
    const [editUserId, setEditUserId] = useState(null);

    const fetchUsers = async () => {
        try {
            let url = USER_API;
            if (filterType !== 'ALL') {
                url = `${USER_API}/type/${filterType}`;
            }
            const res = await axios.get(url);
            setUsers(res.data);
        } catch (err) { console.error(err); }
    };

    useEffect(() => { fetchUsers(); }, [filterType]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        let finalFlowerShopId = formData.flowerShopId ? parseInt(formData.flowerShopId) : null;

        // Validation rules
        if (formData.type === 'EMPLOYEE' && !finalFlowerShopId) {
            alert("Flower Shop ID is mandatory for EMPLOYEE.");
            return;
        }
        
        if (formData.type === 'CLIENT' || formData.type === 'ADMIN') {
            finalFlowerShopId = null;
        }

        const data = { ...formData, flowerShopId: finalFlowerShopId };

        try {
            if (editMode) {
                // Update
                data.id = { userId: editUserId };
                await axios.put(USER_API, data);
                alert(t.updateSuccess || "Updated successfully!");
            } else {
                // Create
                await axios.post(USER_API, data);
                alert(t.createSuccess || "Created successfully!");
            }
            fetchUsers();
            handleCancelEdit();
        } catch (err) {
            if (err.response && err.response.status === 409) {
                alert("Username already exists.");
            } else {
                console.error(err);
                alert("Operation failed.");
            }
        }
    };

    const handleDelete = async (userId) => {
        try {
            await axios.delete(`${USER_API}/${userId}`);
            fetchUsers();
        } catch (err) { console.error(err); }
    };

    const handleEdit = (user) => {
        setEditMode(true);
        setEditUserId(user.id.userId);
        setFormData({
            username: user.username,
            password: user.password,
            type: user.type,
            flowerShopId: user.flowerShopId || ''
        });
    };

    const handleCancelEdit = () => {
        setEditMode(false);
        setEditUserId(null);
        setFormData({ username: '', password: '', type: 'CLIENT', flowerShopId: '' });
    };

    const exportData = (type) => {
        const filename = `Users_List_${filterType}`;
        const usersToExport = users.map(u => ({
            id: u.id.userId,
            username: u.username,
            type: u.type,
            flowerShopId: u.flowerShopId || 'N/A'
        }));

        if (type === 'CSV') exportToCSV(filename, usersToExport);
        if (type === 'JSON') exportToJSON(filename, usersToExport);
        if (type === 'XML') exportToXML(filename, usersToExport, 'users', 'user');
        if (type === 'DOC') exportToDOC(filename, "Users List", usersToExport);
    };

    if (viewNotifs) {
        return (
            <div>
                <button className="btn" onClick={() => setViewNotifs(false)}>🔙 Back</button>
                <NotificationsPage lang={lang} />
            </div>
        );
    }

    return (
        <div className="page-container">
            <div className="dashboard-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2>🛡️ {t.adminDashboard}</h2>
                <div>
                    <button className="btn" style={{marginRight:'5px'}} onClick={() => exportData('CSV')}>{t.exportCSV || "CSV"}</button>
                    <button className="btn" style={{marginRight:'5px'}} onClick={() => exportData('JSON')}>{t.exportJSON || "JSON"}</button>
                    <button className="btn" style={{marginRight:'5px'}} onClick={() => exportData('XML')}>{t.exportXML || "XML"}</button>
                    <button className="btn" style={{marginRight:'5px'}} onClick={() => exportData('DOC')}>{t.exportDOC || "DOC"}</button>
                    <button className="btn btn-primary" onClick={() => setViewNotifs(true)}>{t.notifications}</button>
                </div>
            </div>

            <div className="crud-section">
                <h3>{editMode ? t.edit || "Edit" : t.add || "Add"} User</h3>
                <form onSubmit={handleSubmit} className="crud-form">
                    <input type="text" placeholder={t.username} value={formData.username} onChange={e => setFormData({...formData, username: e.target.value})} required disabled={editMode} title={editMode ? "Username cannot be changed" : ""} />
                    <input type="text" placeholder={t.password} value={formData.password} onChange={e => setFormData({...formData, password: e.target.value})} required />
                    <select value={formData.type} onChange={e => setFormData({...formData, type: e.target.value})}>
                        <option value="CLIENT">CLIENT</option>
                        <option value="EMPLOYEE">EMPLOYEE</option>
                        <option value="MANAGER">MANAGER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                    <input type="number" placeholder={t.flowerShopId} value={formData.flowerShopId} onChange={e => setFormData({...formData, flowerShopId: e.target.value})} disabled={formData.type === 'CLIENT' || formData.type === 'ADMIN'} />
                    <button type="submit" className="btn btn-primary">{t.save}</button>
                    {editMode && <button type="button" className="btn" onClick={handleCancelEdit}>{t.cancel || "Cancel"}</button>}
                </form>
            </div>

            <div className="filters-bar">
                <label>Filter Type: </label>
                <select value={filterType} onChange={e => setFilterType(e.target.value)}>
                    <option value="ALL">ALL</option>
                    <option value="CLIENT">CLIENT</option>
                    <option value="EMPLOYEE">EMPLOYEE</option>
                    <option value="MANAGER">MANAGER</option>
                    <option value="ADMIN">ADMIN</option>
                </select>
            </div>

            <table className="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>{t.username}</th>
                        <th>{t.userType}</th>
                        <th>Shop ID</th>
                        <th>{t.actions}</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(u => (
                        <tr key={u.id.userId}>
                            <td>{u.id.userId}</td>
                            <td>{u.username}</td>
                            <td>{u.type}</td>
                            <td>{u.flowerShopId || '-'}</td>
                            <td>
                                <button className="btn btn-primary" style={{ marginRight: '0.5rem' }} onClick={() => handleEdit(u)}>{t.edit || "Edit"}</button>
                                <button className="btn btn-danger" onClick={() => handleDelete(u.id.userId)}>{t.delete}</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default AdminDashboard;
