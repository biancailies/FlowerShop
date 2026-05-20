import React, { useState } from 'react';
import apiClient, { AUTH_API } from '../api/api';
import { translations } from '../translations/translations';

const Login = ({ lang, onLoginSuccess, onSwitchToRegister }) => {
    const t = translations[lang];
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await apiClient.post(`${AUTH_API}/login`, { username, password });
            if (response.data.success) {
                onLoginSuccess(response.data);
            } else {
                setError(t.loginFailed);
            }
        } catch (err) {
            setError(t.loginFailed);
        }
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <h2>{t.login}</h2>
                {error && <div className="error-message">{error}</div>}
                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label>{t.username}</label>
                        <input 
                            type="text" 
                            value={username} 
                            onChange={(e) => setUsername(e.target.value)} 
                            required 
                        />
                    </div>
                    <div className="form-group">
                        <label>{t.password}</label>
                        <input 
                            type="password" 
                            value={password} 
                            onChange={(e) => setPassword(e.target.value)} 
                            required 
                        />
                    </div>
                    <button type="submit" className="btn btn-primary btn-block">{t.login}</button>
                </form>
                <div style={{ marginTop: '1rem', textAlign: 'center' }}>
                    <p>{t.dontHaveAccount || "Don't have an account?"} <a href="#" onClick={(e) => { e.preventDefault(); onSwitchToRegister(); }}>{t.registerBtn || "Register"}</a></p>
                </div>
            </div>
        </div>
    );
};

export default Login;
