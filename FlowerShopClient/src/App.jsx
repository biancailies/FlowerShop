import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import PublicFlowers from './components/PublicFlowers';
import Login from './components/Login';
import Register from './components/Register';
import EmployeeDashboard from './components/EmployeeDashboard';
import ManagerDashboard from './components/ManagerDashboard';
import AdminDashboard from './components/AdminDashboard';
import { translations } from './translations/translations';
import './styles/style.css';

function App() {
    // Check local storage for user and language
    const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('flowerShopUser')) || null);
    const [lang, setLang] = useState(() => localStorage.getItem('flowerShopLang') || 'en');
    const [showLogin, setShowLogin] = useState(false);
    const [showRegister, setShowRegister] = useState(false);

    useEffect(() => {
        localStorage.setItem('flowerShopLang', lang);
    }, [lang]);

    const handleLoginSuccess = (userData) => {
        setUser(userData);
        localStorage.setItem('flowerShopUser', JSON.stringify(userData));
        setShowLogin(false);
        setShowRegister(false);
    };

    const handleLogout = () => {
        setUser(null);
        localStorage.removeItem('flowerShopUser');
    };

    const t = translations[lang];

    // Conditional rendering based on user type
    const renderContent = () => {
        if (!user) {
            if (showRegister) return <Register lang={lang} onSwitchToLogin={() => { setShowRegister(false); setShowLogin(true); }} />;
            if (showLogin) return <Login lang={lang} onLoginSuccess={handleLoginSuccess} onSwitchToRegister={() => { setShowLogin(false); setShowRegister(true); }} />;
            return (
                <>
                    <div className="hero">
                        <h1>{t.homeTitle}</h1>
                        <p>{t.homeSubtitle}</p>
                        <div style={{ display: 'flex', gap: '1rem', justifyContent: 'center' }}>
                            <button className="btn btn-primary" onClick={() => setShowLogin(true)}>{t.login}</button>
                            <button className="btn" style={{ backgroundColor: '#fff', border: '1px solid #ccc' }} onClick={() => setShowRegister(true)}>{t.registerBtn || "Register"}</button>
                        </div>
                    </div>
                    <PublicFlowers lang={lang} user={user} />
                </>
            );
        }

        switch (user.type) {
            case 'CLIENT':
                return <PublicFlowers lang={lang} user={user} />;
            case 'EMPLOYEE':
                return <EmployeeDashboard lang={lang} user={user} />;
            case 'MANAGER':
                return <ManagerDashboard lang={lang} />;
            case 'ADMIN':
                return <AdminDashboard lang={lang} />;
            default:
                return <PublicFlowers lang={lang} user={user} />;
        }
    };

    return (
        <div className="app-container">
            <Navbar lang={lang} setLang={setLang} user={user} handleLogout={handleLogout} />
            <main className="main-content">
                {renderContent()}
            </main>
        </div>
    );
}

export default App;
