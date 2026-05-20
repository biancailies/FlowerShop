import React, { useState, useEffect } from 'react';
import apiClient, { STATISTICS_API } from '../api/api';
import { translations } from '../translations/translations';

const StatisticsPage = ({ lang }) => {
    const t = translations[lang];
    const [summary, setSummary] = useState(null);
    const [sales, setSales] = useState([]);

    const fetchStats = async () => {
        try {
            const summaryRes = await apiClient.get(`${STATISTICS_API}/sale/summary`);
            setSummary(summaryRes.data);

            const salesRes = await apiClient.get(`${STATISTICS_API}/sale`);
            setSales(salesRes.data.sort((a, b) => new Date(b.saleDate) - new Date(a.saleDate)));
        } catch (err) { console.error(err); }
    };

    useEffect(() => { fetchStats(); }, []);

    // 1. Group by flower name (popular flowers)
    const flowerDataMap = {};
    sales.forEach(s => {
        flowerDataMap[s.flowerName] = (flowerDataMap[s.flowerName] || 0) + s.quantitySold;
    });
    const popularFlowers = Object.entries(flowerDataMap).map(([name, qty]) => ({ name, qty }));
    const maxQty = Math.max(...popularFlowers.map(f => f.qty), 1);

    // 2. Group by shop (Revenue by shop)
    const shopDataMap = {};
    sales.forEach(s => {
        const shopLabel = `Shop ${s.flowerShopId}`;
        shopDataMap[shopLabel] = (shopDataMap[shopLabel] || 0) + s.revenue;
    });
    const shopRevenues = Object.entries(shopDataMap).map(([name, revenue]) => ({ name, revenue }));
    const maxRev = Math.max(...shopRevenues.map(s => s.revenue), 1);

    // 3. Group by flower name (profit by flower)
    const flowerProfitMap = {};
    sales.forEach(s => {
        flowerProfitMap[s.flowerName] = (flowerProfitMap[s.flowerName] || 0) + s.profit;
    });
    const flowerProfits = Object.entries(flowerProfitMap).map(([name, profit]) => ({ name, profit }));
    const maxProfit = Math.max(...flowerProfits.map(f => f.profit), 1);

    return (
        <div className="page-container">
            <h2>📊 {t.statistics}</h2>
            
            {summary && (
                <div className="stats-grid" style={{ display: 'flex', gap: '20px', marginBottom: '40px' }}>
                    <div className="stat-card" style={{ flex: 1, backgroundColor: '#f0fdf4', border: '1px solid #bbf7d0', padding: '20px', borderRadius: '12px', textAlign: 'center' }}>
                        <h3 style={{ margin: 0, color: '#166534' }}>{t.totalSold || "Total Sold"}</h3>
                        <p style={{ fontSize: '32px', margin: '10px 0', fontWeight: 'bold' }}>{summary.totalFlowersSold}</p>
                    </div>
                    <div className="stat-card" style={{ flex: 1, backgroundColor: '#eff6ff', border: '1px solid #bfdbfe', padding: '20px', borderRadius: '12px', textAlign: 'center' }}>
                        <h3 style={{ margin: 0, color: '#1e40af' }}>{t.totalRevenue || "Total Revenue"}</h3>
                        <p style={{ fontSize: '32px', margin: '10px 0', fontWeight: 'bold' }}>${summary.totalRevenue.toFixed(2)}</p>
                    </div>
                    <div className="stat-card" style={{ flex: 1, backgroundColor: '#fdf4ff', border: '1px solid #fbcfe8', padding: '20px', borderRadius: '12px', textAlign: 'center' }}>
                        <h3 style={{ margin: 0, color: '#86198f' }}>{t.totalProfit || "Total Profit"}</h3>
                        <p style={{ fontSize: '32px', margin: '10px 0', fontWeight: 'bold' }}>${summary.totalProfit.toFixed(2)}</p>
                    </div>
                </div>
            )}

            {/* SVG Charts section */}
            <div style={{ display: 'flex', gap: '20px', margin: '30px 0', flexWrap: 'wrap' }}>
                <div style={{ flex: 1, minWidth: '300px', border: '1px solid #eee', padding: '15px', borderRadius: '12px', backgroundColor: '#fff' }}>
                    <h4 style={{ marginTop: 0, marginBottom: '15px', color: '#ff8da1' }}>🌸 Volum vânzări după tipul de floare</h4>
                    {popularFlowers.length > 0 ? (
                        <svg width="100%" height="220" style={{ backgroundColor: '#fafafa', borderRadius: '8px', padding: '10px' }}>
                            {popularFlowers.map((item, idx) => {
                                const barHeight = (item.qty / maxQty) * 130;
                                const x = 20 + idx * 80;
                                const y = 160 - barHeight;
                                return (
                                    <g key={idx}>
                                        <rect x={x} y={y} width="35" height={barHeight} fill="#ff8da1" rx="4" />
                                        <text x={x + 17.5} y={y - 8} textAnchor="middle" fontSize="11" fontWeight="bold" fill="#333">{item.qty} buc</text>
                                        <text x={x + 17.5} y="180" textAnchor="middle" fontSize="9" fontWeight="500" fill="#666">{item.name}</text>
                                    </g>
                                );
                            })}
                            <line x1="10" y1="160" x2="100%" y2="160" stroke="#ddd" strokeWidth="1" />
                        </svg>
                    ) : (
                        <p style={{ color: '#999', fontSize: '14px' }}>Nicio vânzare înregistrată</p>
                    )}
                </div>

                <div style={{ flex: 1, minWidth: '300px', border: '1px solid #eee', padding: '15px', borderRadius: '12px', backgroundColor: '#fff' }}>
                    <h4 style={{ marginTop: 0, marginBottom: '15px', color: '#60a5fa' }}>🏪 Venit generat per Florărie ($)</h4>
                    {shopRevenues.length > 0 ? (
                        <svg width="100%" height="220" style={{ backgroundColor: '#fafafa', borderRadius: '8px', padding: '10px' }}>
                            {shopRevenues.map((item, idx) => {
                                const barHeight = (item.revenue / maxRev) * 130;
                                const x = 20 + idx * 95;
                                const y = 160 - barHeight;
                                return (
                                    <g key={idx}>
                                        <rect x={x} y={y} width="45" height={barHeight} fill="#60a5fa" rx="4" />
                                        <text x={x + 22.5} y={y - 8} textAnchor="middle" fontSize="11" fontWeight="bold" fill="#333">${item.revenue.toFixed(1)}</text>
                                        <text x={x + 22.5} y="180" textAnchor="middle" fontSize="9" fontWeight="500" fill="#666">{item.name}</text>
                                    </g>
                                );
                            })}
                            <line x1="10" y1="160" x2="100%" y2="160" stroke="#ddd" strokeWidth="1" />
                        </svg>
                    ) : (
                        <p style={{ color: '#999', fontSize: '14px' }}>Nicio vânzare înregistrată</p>
                    )}
                </div>

                <div style={{ flex: 1, minWidth: '300px', border: '1px solid #eee', padding: '15px', borderRadius: '12px', backgroundColor: '#fff' }}>
                    <h4 style={{ marginTop: 0, marginBottom: '15px', color: '#10b981' }}>📈 Profit generat per tip de floare ($)</h4>
                    {flowerProfits.length > 0 ? (
                        <svg width="100%" height="220" style={{ backgroundColor: '#fafafa', borderRadius: '8px', padding: '10px' }}>
                            {flowerProfits.map((item, idx) => {
                                const barHeight = (item.profit / maxProfit) * 130;
                                const x = 20 + idx * 80;
                                const y = 160 - barHeight;
                                return (
                                    <g key={idx}>
                                        <rect x={x} y={y} width="35" height={barHeight} fill="#10b981" rx="4" />
                                        <text x={x + 17.5} y={y - 8} textAnchor="middle" fontSize="11" fontWeight="bold" fill="#333">${item.profit.toFixed(1)}</text>
                                        <text x={x + 17.5} y="180" textAnchor="middle" fontSize="9" fontWeight="500" fill="#666">{item.name}</text>
                                    </g>
                                );
                            })}
                            <line x1="10" y1="160" x2="100%" y2="160" stroke="#ddd" strokeWidth="1" />
                        </svg>
                    ) : (
                        <p style={{ color: '#999', fontSize: '14px' }}>Nicio vânzare înregistrată</p>
                    )}
                </div>
            </div>

            <h3>Recent Sales</h3>
            <table className="data-table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Shop ID</th>
                        <th>Flower</th>
                        <th>Qty</th>
                        <th>Revenue</th>
                        <th>Profit</th>
                    </tr>
                </thead>
                <tbody>
                    {sales.slice(0, 10).map(s => (
                        <tr key={s.id}>
                            <td>{new Date(s.saleDate).toLocaleString()}</td>
                            <td>{s.flowerShopId}</td>
                            <td>{s.flowerName} (ID: {s.flowerId})</td>
                            <td>{s.quantitySold}</td>
                            <td>${s.revenue.toFixed(2)}</td>
                            <td><strong style={{color: 'green'}}>${s.profit.toFixed(2)}</strong></td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {sales.length === 0 && <p>No sales recorded yet.</p>}
        </div>
    );
};

export default StatisticsPage;
