import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ReservationPage.css';
import api from '../services/api';  // Axios API instance

const ReservationPage = () => {
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  const [reservationDetails, setReservationDetails] = useState({
    date: '',
    time: '',
    partySize: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setReservationDetails({ ...reservationDetails, [name]: value });
  };

  const handleReservationSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post('/api/reservations', reservationDetails, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Reservation made successfully!');
    } catch (err) {
      console.error('Error making reservation:', err.response);
      alert('Failed to make a reservation. Please try again.');
    }
  };

  return (
    <div className="reservation-page">
      <h1>Make a Reservation</h1>

      {!token ? (
        <div className="login-prompt">
          <p>You need to be logged in to make a reservation.</p>
          <button onClick={() => navigate('/login')} className="login-button">
            Login
          </button>
        </div>
      ) : (
        <form onSubmit={handleReservationSubmit} className="reservation-form">
          <div className="form-group">
            <label htmlFor="date">Date:</label>
            <input
              type="date"
              id="date"
              name="date"
              value={reservationDetails.date}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="time">Time:</label>
            <input
              type="time"
              id="time"
              name="time"
              value={reservationDetails.time}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="partySize">Party Size:</label>
            <input
              type="number"
              id="partySize"
              name="partySize"
              min="1"
              value={reservationDetails.partySize}
              onChange={handleInputChange}
              required
            />
          </div>

          <button type="submit" className="submit-button">
            Submit Reservation
          </button>
        </form>
      )}
    </div>
  );
};

export default ReservationPage;
