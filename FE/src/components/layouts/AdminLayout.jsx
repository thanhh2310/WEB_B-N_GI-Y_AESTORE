import { Link, Outlet } from 'react-router-dom';

const AdminLayout = () => {
  const menuItems = [
    { label: 'User', path: '/admin/users' },
    { label: 'Order', path: '/admin/orders' },
    { label: 'Product', path: '/admin/products' },
    { label: 'Category', path: '/admin/categories' },
    { label: 'Brand', path: '/admin/brands' },
    { label: 'Report', path: '/admin/report' }
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Top Navigation */}
      <nav className="bg-white border-b">
        <div className="px-8 py-4 flex justify-between items-center">
          <Link to="/admin" className="flex items-center gap-2">
            <img src="/nike.svg" alt="Nike Logo" className="h-6" />
            <span className="font-medium">Admin Dashboard</span>
          </Link>
          
          <div className="flex items-center gap-4">
            <Link to="/" className="text-sm">
              Trang chủ
            </Link>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <div className="flex">
        {/* Sidebar */}
        <aside className="w-64 bg-white h-[calc(100vh-73px)] border-r">
          <nav className="p-4">
            <ul className="space-y-2">
              {menuItems.map((item) => (
                <li key={item.label}>
                  <Link
                    to={item.path}
                    className="block px-4 py-2 rounded-md hover:bg-gray-100"
                  >
                    {item.label}
                  </Link>
                </li>
              ))}
            </ul>
          </nav>
        </aside>

        {/* Content Area */}
        <main className="flex-1 p-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default AdminLayout; 