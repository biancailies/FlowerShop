import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { NOTIFICATION_API } from '../api/api';
import { translations } from '../translations/translations';

const NotificationsPage = ({ lang }) => {
    const t = translations[lang];
    const [notifs, setNotifs] = useState([]);
    const [formData, setFormData] = useState({ userId: '', message: '', type: 'EMAIL' });

    const fetchNotifs = async () => {
        try {
            const res = await axios.get(NOTIFICATION_API);
            setNotifs(res.data);
        } catch (err) { console.error(err); }
    };

    useEffect(() => { fetchNotifs(); }, []);

    const handleSend = async (e) => {
        e.preventDefault();
        try {
            await axios.post(`${NOTIFICATION_API}/send`, formData);
            alert("Notification sent!");
            fetchNotifs();
            setFormData({ userId: '', message: '', type: 'EMAIL' });
        } catch (err) { console.error(err); }
    };

    return (
        <div className="page-container">
            <h2>🔔 {t.notifications}</h2>
            
            <div className="crud-section">
                <h3>{t.sendNotification}</h3>
                <form onSubmit={handleSend} className="crud-form">
                    <input type="number" placeholder={t.userId} value={formData.userId} onChange={e => setFormData({...formData, userId: e.target.value})} required />
                    <input type="text" placeholder={t.message} value={formData.message} onChange={e => setFormData({...formData, message: e.target.value})} required />
                    <select value={formData.type} onChange={e => setFormData({...formData, type: e.target.value})}>
                        <option value="EMAIL">EMAIL</option>
                        <option value="SMS">SMS</option>
                        <option value="WHATSAPP">WHATSAPP</option>
                    </select>
                    <button type="submit" className="btn btn-primary">Send</button>
                </form>
            </div>

            <table className="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>User ID</th>
                        <th>{t.type}</th>
                        <th>{t.message}</th>
                    </tr>
                </thead>
                <tbody>
                    {notifs.map(n => (
                        <tr key={n.id.notificationId}>
                            <td>{n.id.notificationId}</td>
                            <td>{n.userId}</td>
                            <td><span className={`badge badge-${n.type.toLowerCase()}`}>{n.type}</span></td>
                            <td>{n.message}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default NotificationsPage;
