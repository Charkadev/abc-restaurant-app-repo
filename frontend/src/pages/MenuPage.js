import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';

const MenuPage = () => {
  const { restaurantId } = useParams(); // Get restaurant ID from URL
  const [menuItems, setMenuItems] = useState([]);
  const [error, setError] = useState('');
  const [cart, setCart] = useState([]);

  useEffect(() => {
    // Fetch menu items for the restaurant
    const fetchMenu = async () => {
      try {
        const response = await api.get(`/api/restaurants/${restaurantId}/menu`); // API call for menu
        setMenuItems(response.data);
      } catch (err) {
        setError('Failed to load menu items.');
      }
    };

    fetchMenu();
  }, [restaurantId]);

  const addToCart = (item) => {
    setCart([...cart, item]);
    alert(`${item.name} added to cart!`);
  };

  if (error) return <p>{error}</p>;

  return (
    <div>
      <h2>Restaurant Menu</h2>
      {menuItems.length === 0 ? (
        <p>No items available.</p>
      ) : (
        <ul>
          {menuItems.map((item) => (
            <li key={item.id}>
              <h3>{item.name}</h3>
              <p>{item.description}</p>
              <p>Price: ${item.price}</p>
              <button onClick={() => addToCart(item)}>Add to Cart</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MenuPage;
