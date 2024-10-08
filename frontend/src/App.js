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
import CartPage from './pages/CartPage';
import ManageUsers from './dashboard/ManageUsers';
import ManageMenu from './dashboard/ManageMenu';
import ManageRestaurants from './dashboard/ManageRestaurants';
import RestaurantsPage from './pages/RestaurantsPage';
import GalleryPage from './pages/GalleryPage';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import PrivateRoute from './components/PrivateRoute';
import CheckoutPage from './pages/CheckoutPage';
import PaymentPage from './pages/PaymentPage';  // Import PaymentPage
import OrderHistoryPage from './pages/OrderHistoryPage';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />

        <div className="App-content">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegistrationPage />} />
            <Route path="/reservation" element={<ReservationPage />} />
            <Route path="/menu" element={<MenuPage />} />
            <Route path="/cart" element={<PrivateRoute role="ROLE_CUSTOMER"><CartPage /></PrivateRoute>} />
            <Route path="/checkout" element={<PrivateRoute role="ROLE_CUSTOMER"><CheckoutPage /></PrivateRoute>} />
            <Route path="/payment/:orderId" element={<PrivateRoute role="ROLE_CUSTOMER"><PaymentPage /></PrivateRoute>} /> {/* Payment route */}
            <Route path="/order-history" element={<PrivateRoute role="ROLE_CUSTOMER"><OrderHistoryPage /></PrivateRoute>} />
            <Route path="/restaurants" element={<RestaurantsPage />} />
            <Route path="/gallery" element={<GalleryPage />} />

            {/* Admin routes */}
            <Route path="/dashboard/admin" element={<PrivateRoute role="ROLE_ADMIN"><AdminDashboard /></PrivateRoute>} />
            <Route path="/dashboard/admin/users" element={<PrivateRoute role="ROLE_ADMIN"><ManageUsers /></PrivateRoute>} />
            <Route path="/dashboard/admin/menu" element={<PrivateRoute role="ROLE_ADMIN"><ManageMenu /></PrivateRoute>} />
            <Route path="/dashboard/admin/restaurants" element={<PrivateRoute role="ROLE_ADMIN"><ManageRestaurants /></PrivateRoute>} />

            {/* Protected routes based on roles */}
            <Route path="/dashboard/staff" element={<PrivateRoute role="ROLE_RESTAURANT_STAFF"><StaffDashboard /></PrivateRoute>} />
            <Route path="/dashboard/customer" element={<PrivateRoute role="ROLE_CUSTOMER"><CustomerDashboard /></PrivateRoute>} />
          </Routes>
        </div>

        <Footer />
      </div>
    </Router>
  );
}

export default App;
