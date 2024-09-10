import React, { useState, useEffect } from 'react';
import api from '../services/api';

const OrderHistoryPage = () => {
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchOrderHistory = async () => {
      try {
        const response = await api.get('/api/orders'); // Replace with your backend endpoint
        setOrders(response.data);
      } catch (err) {
        setError('Failed to load order history.');
      }
    };

    fetchOrderHistory();
  }, []);

  if (error) return <p>{error}</p>;

  return (
    <div>
      <h2>Order History</h2>
      {orders.length === 0 ? (
        <p>No orders found.</p>
      ) : (
        <ul>
          {orders.map((order) => (
            <li key={order.id}>
              <h3>Order ID: {order.id}</h3>
              <p>Total: ${order.total}</p>
              <p>Status: {order.status}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default OrderHistoryPage;
