import { useState } from 'react';
import { Link, Outlet, useLocation, useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';

const AdminLayout = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [openDropdown, setOpenDropdown] = useState({});

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    toast.success('Đăng xuất thành công');
    navigate('/signin');
  };

  const menuItems = [
    {
      title: 'User',
      path: '/admin/users',
      icon: <svg>...</svg>
    },
    {
      title: 'Order', 
      path: '/admin/orders',
      icon: <svg>...</svg>
    },
    {
      title: 'Product',
      path: '/admin/products',
      icon: <svg>...</svg>,
      subItems: [
        { title: 'Color', path: '/admin/colors' },
        { title: 'Brand', path: '/admin/brands' },
        { title: 'Category', path: '/admin/categories' },
        { title: 'Size', path: '/admin/sizes' },
      ]
    },
    // ... other menu items ...
  ];

  return (
    <div className="flex h-screen">
      <div className="w-64 bg-white border-r">
        <div className="flex items-center gap-2 px-4 py-6 border-b">
          <img src="/nike-logo.png" alt="Nike Logo" className="w-10" />
          <span className="font-medium">Admin Dashboard</span>
        </div>
        <nav className="p-4">
          <ul className="space-y-2">
            {menuItems.map((item, index) => (
              <li key={item.title}>
                {item.subItems ? (
                  <div
                    onClick={() => setOpenDropdown(prev => ({
                      ...prev,
                      [index]: !prev[index]
                    }))}
                    className={`flex items-center justify-between p-4 text-gray-700 hover:bg-gray-100 cursor-pointer ${
                      location.pathname === item.path ? 'bg-gray-100' : ''
                    }`}
                  >
                    <div className="flex items-center">
                      {item.icon}
                      <span className="ml-2">{item.title}</span>
                    </div>
                    <span>{openDropdown[index] ? '▲' : '▼'}</span>
                  </div>
                ) : (
                  <Link
                    to={item.path}
                    className={`flex items-center p-4 text-gray-700 hover:bg-gray-100 ${
                      location.pathname === item.path ? 'bg-gray-100' : ''
                    }`}
                  >
                    {item.icon}
                    <span className="ml-2">{item.title}</span>
                  </Link>
                )}
                {item.subItems && openDropdown[index] && (
                  <ul className="pl-6 bg-gray-50">
                    {item.subItems.map((subItem) => (
                      <li key={subItem.title}>
                        <Link 
                          to={subItem.path}
                          className={`block p-2 text-gray-600 hover:bg-gray-200 ${
                            location.pathname === subItem.path ? 'bg-gray-200' : ''
                          }`}
                        >
                          {subItem.title}
                        </Link>
                      </li>
                    ))}
                  </ul>
                )}
              </li>
            ))}
          </ul>
        </nav>
      </div>

      <div className="flex-1 flex flex-col">
        <header className="bg-white border-b">
          <div className="flex justify-between items-center px-8 py-6">
            <h1 className="text-2xl font-medium">Admin Dashboard</h1>
            <button 
              onClick={handleLogout}
              className="px-4 py-2 text-gray-500 hover:text-gray-700 hover:bg-gray-100 rounded-md transition-colors"
            >
              Logout
            </button>
          </div>
        </header>
        <main className="flex-1 overflow-auto p-8 bg-gray-50">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default AdminLayout;