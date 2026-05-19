import React, { useState } from 'react';
import axios from 'axios';
import { USER_API, NOTIFICATION_API } from '../api/api';
import { translations } from '../translations/translations';

const Register = ({ lang, onSwitchToLogin }) => {
    const t = translations[lang];
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const [successMsg, setSuccessMsg] = useState('');

    const handleRegister = async (e) => {
        e.preventDefault();
        setError('');
        setSuccessMsg('');

        if (password !== confirmPassword) {
            setError(t.passwordsMismatch || "Passwords do not match.");
            return;
        }

        try {
            const userData = {
                username: username,
                password: password,
                type: "CLIENT",
                flowerShopId: null
            };

            const response = await axios.post(USER_API, userData);
            const createdUserId = response.data; // Backend now returns the created ID as string

            setSuccessMsg(t.registerSuccess || "Account created successfully.");
            
            // BONUS: Send Welcome Notification
            try {
                if (createdUserId && !isNaN(createdUserId)) {
                    const userId = parseInt(createdUserId);
                    await Promise.all([
                        axios.post(`${NOTIFICATION_API}/send`, {
                            userId,
                            message: "Welcome to BloomChain!",
                            type: "EMAIL",
                            recipientEmail: username
                        }),
                        axios.post(`${NOTIFICATION_API}/send`, {
                            userId,
                            message: `New BloomChain account created for ${username}.`,
                            type: "DISCORD"
                        })
                    ]);
                }
            } catch (notifErr) {
                console.error("Failed to send welcome notification", notifErr);
            }

            // Switch to login after 2 seconds
            setTimeout(() => {
                onSwitchToLogin();
            }, 2000);

        } catch (err) {
            if (err.response && err.response.status === 409) {
                setError(err.response.data || "Username already exists.");
            } else if (err.response && err.response.status === 400) {
                setError(err.response.data || "Validation error.");
            } else {
                setError("Registration failed. Please try again.");
            }
        }
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <h2>{t.register || "Register"}</h2>
                {error && <div className="error-message">{error}</div>}
                {successMsg && <div className="success-message" style={{ color: 'green', backgroundColor: '#e8f5e9', padding: '0.8rem', borderRadius: '6px', marginBottom: '1rem', textAlign: 'center' }}>{successMsg}</div>}
                <form onSubmit={handleRegister}>
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
                    <div className="form-group">
                        <label>{t.confirmPassword || "Confirm Password"}</label>
                        <input 
                            type="password" 
                            value={confirmPassword} 
                            onChange={(e) => setConfirmPassword(e.target.value)} 
                            required 
                        />
                    </div>
                    <button type="submit" className="btn btn-primary btn-block">{t.registerBtn || "Create Account"}</button>
                </form>
                <div style={{ marginTop: '1rem', textAlign: 'center' }}>
                    <p>{t.alreadyHaveAccount || "Already have an account?"} <a href="#" onClick={(e) => { e.preventDefault(); onSwitchToLogin(); }}>{t.login}</a></p>
                </div>
            </div>
        </div>
    );
};

export default Register;
