import React from 'react';
import { Link } from 'react-router-dom';

const AdminDashboard = () => {
  return (
    <div>
      <h1>Admin Dashboard</h1>
      <ul>
        <li><Link to="/dashboard/admin/users">Manage Users</Link></li>
        <li><Link to="/dashboard/admin/menu">Manage Menu Items</Link></li>
        <li><Link to="/dashboard/admin/restaurants">Manage Restaurants</Link></li>  {/* Link to manage restaurants */}
      </ul>
    </div>
  );
};

export default AdminDashboard;
