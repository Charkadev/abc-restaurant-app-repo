import React, { useState, useEffect } from 'react';
import api from '../services/api';

const StaffDashboard = () => {
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const response = await api.get('/api/reservations/all');
        setReservations(response.data);
      } catch (error) {
        console.error('Error fetching reservations:', error);
      }
    };

    fetchReservations();
  }, []);

  return (
    <div>
      <h1>Staff Dashboard</h1>
      <h2>Customer Reservations</h2>
      {reservations.length > 0 ? (
        <ul>
          {reservations.map((reservation) => (
            <li key={reservation.id}>
              <strong>Date:</strong> {reservation.date} <br />
              <strong>Time:</strong> {reservation.time} <br />
              <strong>Party Size:</strong> {reservation.partySize} <br />
              <strong>Status:</strong> {reservation.status} <br />
              <strong>Customer:</strong> {reservation.customer.fullName}
            </li>
          ))}
        </ul>
      ) : (
        <p>No reservations found.</p>
      )}
    </div>
  );
};

export default StaffDashboard;
