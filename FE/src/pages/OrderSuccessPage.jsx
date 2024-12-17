import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-hot-toast';

const OrderSuccessPage = () => {
  const { orderId } = useParams();
  const [order, setOrder] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchOrder = async () => {
      try {
        const username = JSON.parse(localStorage.getItem('user')).username;
        const response = await axios.get(`http://localhost:8081/saleShoes/orders/username?username=${username}`);
        if (response.data?.result) {
          setOrder(response.data.result);
        }
      } catch (error) {
        console.error('Error fetching order:', error);
        toast.error('Không thể tải thông tin đơn hàng');
      } finally {
        setLoading(false);
      }
    };

    if (orderId) {
      fetchOrder();
    }
  }, [orderId]);

  if (loading) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="text-center">Đang tải...</div>
      </div>
    );
  }

  if (!order) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="text-center">Không tìm thấy đơn hàng</div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto text-center">
        <div className="mb-8">
          <svg className="w-20 h-20 text-green-500 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7" />
          </svg>
        </div>

        <h1 className="text-3xl font-bold mb-4">Đặt hàng thành công!</h1>
        <p className="text-gray-600 mb-8">
          Cảm ơn bạn đã đặt hàng. Mã đơn hàng của bạn là: <span className="font-medium">{order.id}</span>
        </p>

        <div className="bg-gray-50 p-6 rounded-lg text-left mb-8">
          <h2 className="font-semibold mb-4">Chi tiết đơn hàng</h2>
          <div className="space-y-4">
            {order.orderDetail.map((item) => (
              <div key={item.id} className="flex gap-4">
                <img
                  src={item.productDetail.image[0]}
                  alt=""
                  className="w-20 h-20 object-cover rounded"
                />
                <div>
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

        <div className="space-x-4">
          <Link
            to="/history"
            className="inline-block px-6 py-3 bg-black text-white rounded-full hover:bg-gray-800"
          >
            Xem đơn hàng
          </Link>
          <Link
            to="/"
            className="inline-block px-6 py-3 border border-black rounded-full hover:bg-gray-100"
          >
            Tiếp tục mua sắm
          </Link>
        </div>
      </div>
    </div>
  );
};

export default OrderSuccessPage; 