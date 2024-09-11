import React from 'react';
import { Link } from 'react-router-dom';

const AdminDashboard = () => {
  return (
    <div>
      <h1>Admin Dashboard</h1>
      <ul>
        <li><Link to="/dashboard/admin/users">Manage Users</Link></li>
        <li><Link to="/dashboard/admin/menu">Manage Menu Items</Link></li>
        {/* You can add more links here like "Generate Reports" later */}
      </ul>
    </div>
  );
};

export default AdminDashboard;

