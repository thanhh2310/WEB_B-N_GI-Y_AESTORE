import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-hot-toast';

const LoginPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    
    try {
      const loginData = {
        username: formData.username.trim(),
        password: formData.password
      };

      const response = await axios.post(
        'http://localhost:8081/saleShoes/auth/log-in', 
        loginData,
        {
          headers: {
            'Content-Type': 'application/json'
          }
        }
      );

      if (response.data?.result) {
        const userData = response.data.result;
        console.log('User data from server:', userData);
        
        try {
          const token = userData.token;
          const tokenParts = token.split('.');
          const payload = JSON.parse(atob(tokenParts[1]));
          const isAdmin = payload.sub === 'admin';

          console.log('Payload:', payload);
          console.log('Is admin:', isAdmin);

          if (isAdmin) {
            localStorage.setItem('token', userData.token);
            localStorage.setItem('user', JSON.stringify({
              username: formData.username,
              token: userData.token,
              authenticate: userData.authenticate,
              sub: payload.sub,
              role: 'ADMIN'
            }));
            
            toast.success('Đăng nhập thành công với quyền Admin');
            navigate('/admin/dashboard');
          } else {
            if (!userData.authenticate) {
              setError('Tài khoản chưa được xác thực');
              toast.error('Vui lòng xác thực email trước khi đăng nhập');
              return;
            }

            localStorage.setItem('token', userData.token);
            localStorage.setItem('user', JSON.stringify({
              username: formData.username,
              token: userData.token,
              authenticate: userData.authenticate,
              sub: payload.sub,
              role: 'USER'
            }));

            toast.success('Đăng nhập thành công');
            navigate('/');
          }
        } catch (tokenError) {
          console.error('Token parsing error:', tokenError);
          setError('Lỗi xử lý token');
          toast.error('Đăng nhập thất bại');
        }
      } else {
        setError('Đăng nhập thất bại');
        toast.error('Đăng nhập thất bại');
      }
    } catch (error) {
      console.error('Login error:', error);
      console.log('Error response data:', error.response?.data);
      
      if (error.response?.data?.code === 1017) {
        setError('Tài khoản chưa được xác nhận');
        toast.error('Tài khoản chưa được xác nhận. Vui lòng kiểm tra email để xác nhận tài khoản.');
      } else if (error.response?.data?.message === 'User is inactive') {
        setError('Tài khoản đã bị vô hiệu hóa');
        toast.error('Tài khoản đã bị vô hiệu hóa');
      } else {
        setError(error.response?.data?.message || 'Đăng nhập thất bại');
        toast.error('Đăng nhập thất bại');
      }
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <Link to="/">
          <img className="mx-auto h-12 w-auto" src="/nike.svg" alt="Nike" />
        </Link>
        <h2 className="mt-6 text-center text-3xl font-bold text-gray-900">
          YOUR ACCOUNT FOR EVERYTHING NIKE
        </h2>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
          <form className="space-y-6" onSubmit={handleSubmit}>
            {error && (
              <div className="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-md text-sm">
                {error}
              </div>
            )}

            <div>
              <label htmlFor="username" className="block text-sm font-medium text-gray-700">
                Username
              </label>
              <div className="mt-1">
                <input
                  id="username"
                  name="username"
                  type="text"
                  required
                  value={formData.username}
                  onChange={handleChange}
                  className={`appearance-none block w-full px-3 py-2 border ${
                    error ? 'border-red-300' : 'border-gray-300'
                  } rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-black focus:border-black sm:text-sm`}
                />
              </div>
            </div>

            <div>
              <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                Password
              </label>
              <div className="mt-1">
                <input
                  id="password"
                  name="password"
                  type="password"
                  autoComplete="current-password"
                  required
                  value={formData.password}
                  onChange={handleChange}
                  className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-black focus:border-black sm:text-sm"
                />
              </div>
            </div>

            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <input
                  id="remember-me"
                  name="remember-me"
                  type="checkbox"
                  className="h-4 w-4 text-black focus:ring-black border-gray-300 rounded"
                />
                <label htmlFor="remember-me" className="ml-2 block text-sm text-gray-900">
                  Keep me signed in
                </label>
              </div>

              <div className="text-sm">
                <Link to="/forgot-password" className="font-medium text-gray-600 hover:text-gray-500">
                  Forgot your password?
                </Link>
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-black hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black"
              >
                SIGN IN
              </button>
            </div>
          </form>

          <div className="mt-6">
            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-gray-300" />
              </div>
              <div className="relative flex justify-center text-sm">
                <span className="px-2 bg-white text-gray-500">Not a Member?</span>
              </div>
            </div>

            <div className="mt-6">
              <Link
                to="/register"
                className="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black"
              >
                Join Us
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage; 