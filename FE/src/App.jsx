import { Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import HomePage from './pages/HomePage';
import ProductDetail from './pages/ProductDetail';
import CartPage from './pages/CartPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import AdminLayout from './components/layouts/AdminLayout';
import AdminUsersPage from './pages/admin/AdminUsersPage';
import AdminOrderPage from './pages/admin/AdminOrderPage';
import AdminProductPage from './pages/admin/AdminProductPage';
import AdminCategoryPage from './pages/admin/AdminCategoryPage';
import AdminBrandPage from './pages/admin/AdminBrandPage';
import AdminReportPage from './pages/admin/AdminReportPage';

function App() {
  return (
    <Routes>
      {/* Auth Routes - No Header/Footer */}
      <Route path="/signin" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />

      {/* Client Routes - With Header/Footer */}
      <Route path="/*" element={
        <div className="font-nike min-h-screen flex flex-col">
          <Header />
          <main className="flex-grow">
            <Routes>
              <Route index element={<HomePage />} />
              <Route path="product/:id" element={<ProductDetail />} />
              <Route path="cart" element={<CartPage />} />
            </Routes>
          </main>
          <Footer />
        </div>
      } />

      {/* Admin Routes */}
      <Route path="/admin/*" element={<AdminLayout />}>
        <Route index element={<AdminUsersPage />} />
        <Route path="users" element={<AdminUsersPage />} />
        <Route path="orders" element={<AdminOrderPage />} />
        <Route path="products" element={<AdminProductPage />} />
        <Route path="categories" element={<AdminCategoryPage />} />
        <Route path="brands" element={<AdminBrandPage />} />
        <Route path="report" element={<AdminReportPage />} />
      </Route>
    </Routes>
  );
}

export default App;
