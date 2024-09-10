import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Navbar.css';  // Assuming this is where your CSS is

const Navbar = () => {
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <Link to="/" className="navbar-brand">ABC Restaurant</Link>
        <Link to="/" className="navbar-link">Home</Link>
        <Link to="/restaurants" className="navbar-link">Restaurants</Link>
        <Link to="/menu" className="navbar-link">Menu</Link>
        <Link to="/gallery" className="navbar-link">Gallery</Link>
        <Link to="/reservation" className="navbar-link">Reservations</Link>
        <Link to="/contact" className="navbar-link">Contact</Link>
      </div>
      <div className="navbar-right">
        {/* Cart button with only the icon */}
        <Link to="/cart" className="navbar-link navbar-cart">
          <i className="fas fa-shopping-cart"></i>
        </Link>

        {/* Conditional rendering for logged-in user options */}
        {token && role === 'CUSTOMER' && (
          <Link to="/profile" className="navbar-link">Profile</Link>
        )}

        {token && role === 'RESTAURANT_STAFF' && (
          <Link to="/dashboard/staff" className="navbar-link">Staff Dashboard</Link>
        )}

        {token && role === 'ADMIN' && (
          <Link to="/dashboard/admin" className="navbar-link">Admin Dashboard</Link>
        )}

        {/* Login/Register or Logout */}
        {token ? (
          <button className="navbar-button" onClick={handleLogout}>Logout</button>
        ) : (
          <>
            <Link to="/login" className="navbar-link">Login</Link>
            <Link to="/register" className="navbar-link">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
