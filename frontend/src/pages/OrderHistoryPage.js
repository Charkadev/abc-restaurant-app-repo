import React, { useState, useEffect } from 'react';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';

const OrderHistoryPage = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchOrders = async () => {
      setError('');
      setLoading(true);
      try {
        const response = await api.get('/api/order/user', { // Changed to correct API endpoint
          headers: { Authorization: `Bearer ${token}` }
        });
        setOrders(response.data);
      } catch (err) {
        setError('Failed to load orders');
        console.error('Error fetching orders:', err.response?.data || err.message);
      }
      setLoading(false);
    };

    fetchOrders();
  }, [token]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;
  if (orders.length === 0) return <p>No orders found</p>;

  return (
    <div>
      <h1>Your Order History</h1>
      <ul>
        {orders.map(order => (
          <li key={order.id}>
            <h2>Order ID: {order.id}</h2>
            <p>Status: {order.orderStatus}</p>
            <p>Delivery Address: {order.deliveryAddress}</p>
            <p>Total Price: ${order.totalPrice}</p>
            <p>Ordered on: {new Date(order.createdAt).toLocaleString()}</p>
            <ul>
              {order.items.map(item => (
                <li key={item.id}>
                  {item.food.item_name} - {item.quantity} x ${item.food.price} = ${item.totalPrice}
                </li>
              ))}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default OrderHistoryPage;
