import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import OrderTimeline from '../components/OrderTimeline';
import { getOrderTimeline } from '../data/orderStatus';
import axios from 'axios';

const OrderHistory = () => {
  const [filterStatus, setFilterStatus] = useState('all');
  const navigate = useNavigate();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user) {
          toast.error('Vui lòng đăng nhập để xem lịch sử đơn hàng');
          navigate('/signin');
          return;
        }

        const response = await axios.get(`http://localhost:8081/saleShoes/ordersusername?username=${user.username}`);
        if (response.data?.result) {
          setOrders(response.data.result);
        }
      } catch (error) {
        console.error('Error fetching orders:', error);
        toast.error('Không thể tải lịch sử đơn hàng');
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, [navigate]);

  // Hàm thêm vào giỏ hàng
  const addToCart = (item) => {
    // Tạo object sản phẩm để thêm vào giỏ hàng
    const cartItem = {
      productId: item.productId,
      name: item.name,
      price: item.price,
      color: item.color,
      size: item.size,
      quantity: 1,
      image: item.image
    };

    // Lấy giỏ hàng hiện tại từ localStorage
    const currentCart = JSON.parse(localStorage.getItem('cart') || '[]');
    
    // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
    const existingItemIndex = currentCart.findIndex(
      cartItem => cartItem.productId === item.productId && 
                 cartItem.color === item.color && 
                 cartItem.size === item.size
    );

    if (existingItemIndex !== -1) {
      // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng
      currentCart[existingItemIndex].quantity += 1;
    } else {
      // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
      currentCart.push(cartItem);
    }

    // Lưu giỏ hàng mới vào localStorage
    localStorage.setItem('cart', JSON.stringify(currentCart));
    
    // Hiển thị thông báo
    toast.success('Đã thêm sản phẩm vào giỏ hàng!');
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'pending':
        return 'bg-yellow-100 text-yellow-800';
      case 'completed':
        return 'bg-green-100 text-green-800';
      case 'cancelled':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  const getStatusText = (status) => {
    switch (status) {
      case 'pending':
        return 'Đang xử lý';
      case 'completed':
        return 'Đã hoàn thành';
      case 'cancelled':
        return 'Đã hủy';
      default:
        return status;
    }
  };

  const filteredOrders = orders.filter(order => 
    filterStatus === 'all' ? true : order.status === filterStatus
  );

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold mb-6">Lịch sử đơn hàng</h1>

      {loading ? (
        <div>Đang tải...</div>
      ) : (
        <div className="space-y-6">
          {orders.map((order) => (
            <div key={order.id} className="border rounded-lg p-6 bg-white shadow-sm">
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h2 className="text-lg font-semibold">Đơn hàng #{order.id}</h2>
                  <p className="text-gray-600">
                    Ngày đặt: {new Date(order.dateCreate).toLocaleDateString('vi-VN')}
                  </p>
                </div>
                <div className="text-right">
                  <span className={`px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(order.status)}`}>
                    {getStatusText(order.status)}
                  </span>
                </div>
              </div>

              <div className="space-y-4">
                {order.orderDetail.map((item) => (
                  <div key={item.id} className="flex gap-4 border-t pt-4">
                    <img
                      src={item.productDetail.image[0]}
                      alt=""
                      className="w-24 h-24 object-cover rounded"
                    />
                    <div className="flex-grow">
                      <p className="font-medium">Size: {item.productDetail.size}</p>
                      <p>Màu: {item.productDetail.color}</p>
                      <p>Số lượng: {item.quantity}</p>
                      <p>Giá: {item.price.toLocaleString('vi-VN')}₫</p>
                    </div>
                  </div>
                ))}
              </div>

              <div className="mt-4 pt-4 border-t">
                <p>Địa chỉ: {order.address}</p>
                <p className="font-medium">
                  Tổng tiền: {order.orderDetail.reduce((total, item) => total + item.price * item.quantity, 0).toLocaleString('vi-VN')}₫
                </p>
              </div>
            </div>
          ))}

          {orders.length === 0 && (
            <div className="text-center py-8 text-gray-500">
              Bạn chưa có đơn hàng nào
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default OrderHistory; 