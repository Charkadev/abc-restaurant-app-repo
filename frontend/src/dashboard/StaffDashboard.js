import React, { useEffect, useState } from 'react';
import api from '../services/api';

const StaffDashboard = () => {
  const [orders, setOrders] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [queries, setQueries] = useState([]);
  const [response, setResponse] = useState('');

  // Fetch Orders
  useEffect(() => {
    const fetchOrders = async () => {
      const response = await api.get('/api/staff/orders');
      setOrders(response.data);
    };
    fetchOrders();
  }, []);

  // Fetch Reservations
  useEffect(() => {
    const fetchReservations = async () => {
      const response = await api.get('/api/staff/reservations');
      setReservations(response.data);
    };
    fetchReservations();
  }, []);

  // Fetch Queries
  useEffect(() => {
    const fetchQueries = async () => {
      const response = await api.get('/api/staff/queries');
      setQueries(response.data);
    };
    fetchQueries();
  }, []);

  const handleRespondToQuery = async (id) => {
    await api.post(`/api/staff/queries/respond/${id}`, { response });
    alert('Response sent!');
  };

  return (
    <div>
      <h1>Staff Dashboard</h1>

      <h2>Orders</h2>
      <ul>
        {orders.map(order => (
          <li key={order.id}>
            Order #{order.id} - ${order.totalPrice}
          </li>
        ))}
      </ul>

      <h2>Reservations</h2>
      <ul>
        {reservations.map(reservation => (
          <li key={reservation.id}>
            Reservation #{reservation.id} - {reservation.date}
          </li>
        ))}
      </ul>

      <h2>Customer Queries</h2>
      <ul>
        {queries.map(query => (
          <li key={query.id}>
            Query #{query.id}: {query.message}
            <input type="text" placeholder="Response" onChange={(e) => setResponse(e.target.value)} />
            <button onClick={() => handleRespondToQuery(query.id)}>Send Response</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StaffDashboard;
