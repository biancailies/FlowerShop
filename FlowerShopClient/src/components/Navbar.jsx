import React from 'react';
import { translations } from '../translations/translations';

const Navbar = ({ lang, setLang, user, handleLogout }) => {
    const t = translations[lang];

    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <h2>🌺 BloomChain</h2>
            </div>
            <div className="navbar-controls">
                <select 
                    className="lang-select" 
                    value={lang} 
                    onChange={(e) => setLang(e.target.value)}
                >
                    <option value="en">English</option>
                    <option value="ro">Română</option>
                    <option value="fr">Français</option>
                </select>

                {user ? (
                    <div className="user-controls">
                        <span className="user-badge">{t.loggedInAs} {user.username} ({user.type})</span>
                        <button className="btn btn-logout" onClick={handleLogout}>{t.logout}</button>
                    </div>
                ) : (
                    // Navbar should not have login button since Login is rendered if not authenticated (or conditionally)
                    // The prompt asked for Login if not logged in.
                    <span className="user-badge">{t.publicView}</span>
                )}
            </div>
        </nav>
    );
};

export default Navbar;
