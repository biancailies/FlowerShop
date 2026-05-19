import React from 'react';
import { translations } from '../translations/translations';

const FlowerCard = ({ flower, lang, onSell, showSell }) => {
    const t = translations[lang];

    return (
        <div className="flower-card">
            <div className="flower-card-img-placeholder" style={{ padding: 0, overflow: 'hidden' }}>
                <img 
                    src={flower.imageUrl || "https://images.unsplash.com/photo-1490750967868-88aa4486c946?w=400&q=80"} 
                    alt={flower.flowerName} 
                    style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                />
            </div>
            <div className="flower-card-content">
                <h3>{flower.flowerName}</h3>
                <p><strong>{t.flowerShop}:</strong> {flower.flowerShopName}</p>
                <p><strong>{t.color}:</strong> {flower.color}</p>
                <p><strong>{t.price}:</strong> ${flower.sellingPrice}</p>
                <p><strong>{t.stock}:</strong> <span className={flower.quantity > 0 ? 'stock-ok' : 'stock-empty'}>{flower.quantity}</span></p>
                
                {showSell && flower.quantity > 0 && (
                    <button className="btn btn-primary" onClick={() => onSell(flower.stockId)}>
                        {t.sell} 1
                    </button>
                )}
            </div>
        </div>
    );
};

export default FlowerCard;
