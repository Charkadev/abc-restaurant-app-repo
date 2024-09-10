import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegistrationPage from './pages/RegistrationPage';
import AdminDashboard from './dashboard/AdminDashboard';
import StaffDashboard from './dashboard/StaffDashboard';
import CustomerDashboard from './dashboard/CustomerDashboard';
import ReservationPage from './pages/ReservationPage';
import MenuPage from './pages/MenuPage';
import CartPage from './pages/CartPage';  // Import the CartPage
import Navbar from './components/Navbar';
import PrivateRoute from './components/PrivateRoute';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegistrationPage />} />
          <Route path="/reservation" element={<ReservationPage />} />
          <Route path="/menu" element={<MenuPage />} />
          <Route path="/cart" element={<PrivateRoute><CartPage /></PrivateRoute>} /> {/* Add CartPage route */}

          {/* Protected routes based on roles */}
          <Route path="/dashboard/admin" element={<PrivateRoute><AdminDashboard /></PrivateRoute>} />
          <Route path="/dashboard/staff" element={<PrivateRoute><StaffDashboard /></PrivateRoute>} />
          <Route path="/dashboard/customer" element={<PrivateRoute><CustomerDashboard /></PrivateRoute>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
