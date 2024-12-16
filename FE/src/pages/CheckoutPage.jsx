import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import { getProvinces, getDistricts, getWards, calculateShippingFee } from '../services/addressApi';
import { ORDER_STATUS } from '../data/orderStatus';

const CheckoutPage = () => {
  const navigate = useNavigate();
  const [cartItems, setCartItems] = useState([]);
  const [appliedVouchers, setAppliedVouchers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [provinces, setProvinces] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [wards, setWards] = useState([]);
  const [selectedProvince, setSelectedProvince] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [shippingFee, setShippingFee] = useState(0);
  const [checkoutItems, setCheckoutItems] = useState([]);

  // Form state
  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    phone: '',
    address: '',
    city: '',
    district: '',
    ward: '',
    note: '',
    paymentMethod: 'cod' // cod, banking, momo
  });

  // Tính toán giá tiền
  const subtotal = cartItems.reduce((total, item) => 
    total + (item.price * item.quantity), 0
  );
  const total = subtotal + shippingFee;

  useEffect(() => {
    // Load cart items
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      setCartItems(JSON.parse(savedCart));
    }

    // Load applied vouchers
    const savedVouchers = localStorage.getItem('appliedVouchers');
    if (savedVouchers) {
      setAppliedVouchers(JSON.parse(savedVouchers));
    }
  }, []);

  useEffect(() => {
    // Load tỉnh/thành phố
    const loadProvinces = async () => {
      try {
        const data = await getProvinces();
        setProvinces(data);
      } catch (error) {
        toast.error('Không thể tải danh sách tỉnh thành');
      }
    };
    loadProvinces();
  }, []);

  // Load quận/huyện khi chọn tỉnh/thành
  useEffect(() => {
    const loadDistricts = async () => {
      if (selectedProvince) {
        try {
          const data = await getDistricts(selectedProvince);
          setDistricts(data);
          setSelectedDistrict('');
          setWards([]);
          // Tính phí ship
          const fee = calculateShippingFee(selectedProvince);
          setShippingFee(fee);
        } catch (error) {
          toast.error('Không thể tải danh sách quận huyện');
        }
      }
    };
    loadDistricts();
  }, [selectedProvince]);

  // Load phường/xã khi chọn quận/huyện
  useEffect(() => {
    const loadWards = async () => {
      if (selectedDistrict) {
        try {
          const data = await getWards(selectedDistrict);
          setWards(data);
        } catch (error) {
          toast.error('Không thể tải danh sách phường xã');
        }
      }
    };
    loadWards();
  }, [selectedDistrict]);

  useEffect(() => {
    // Load checkout items
    const savedCheckoutItems = localStorage.getItem('checkoutItems');
    if (savedCheckoutItems) {
      setCheckoutItems(JSON.parse(savedCheckoutItems));
    } else {
      // Nếu không có checkoutItems, lấy từ cart
      const savedCart = localStorage.getItem('cart');
      if (savedCart) {
        setCheckoutItems(JSON.parse(savedCart));
      }
    }
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const validateForm = () => {
    // Kiểm tra các trường bắt buộc
    if (!formData.fullName) {
      toast.error('Vui lòng nhập họ tên');
      return false;
    }
    if (!formData.email) {
      toast.error('Vui lòng nhập email');
      return false;
    }
    if (!formData.phone) {
      toast.error('Vui lòng nhập số điện thoại');
      return false;
    }
    if (!formData.address) {
      toast.error('Vui lòng nhập địa chỉ');
      return false;
    }
    if (!selectedProvince) {
      toast.error('Vui lòng chọn Tỉnh/Thành');
      return false;
    }
    if (!selectedDistrict) {
      toast.error('Vui lòng chọn Quận/Huyện');
      return false;
    }
    if (!formData.ward) {
      toast.error('Vui lòng chọn Phường/Xã');
      return false;
    }

    // Validate email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      toast.error('Email không hợp lệ');
      return false;
    }

    // Validate phone
    const phoneRegex = /(84|0[3|5|7|8|9])+([0-9]{8})\b/;
    if (!phoneRegex.test(formData.phone)) {
      toast.error('Số điện thoại không hợp lệ');
      return false;
    }

    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) return;
    
    setLoading(true);

    try {
      await new Promise(resolve => setTimeout(resolve, 1500));

      const selectedProvinceName = provinces.find(p => p.code === selectedProvince)?.name;
      const selectedDistrictName = districts.find(d => d.code === selectedDistrict)?.name;
      const selectedWardName = wards.find(w => w.code === formData.ward)?.name;

      const order = {
        id: `ORD${Date.now()}`,
        items: cartItems,
        vouchers: appliedVouchers,
        shipping: {
          ...formData,
          province: selectedProvinceName,
          district: selectedDistrictName,
          ward: selectedWardName,
          fee: shippingFee
        },
        payment: {
          method: formData.paymentMethod,
          subtotal,
          shipping: shippingFee,
          discount: 0,
          total: total + shippingFee
        },
        status: ORDER_STATUS.PENDING,
        timeline: [
          {
            status: ORDER_STATUS.PENDING,
            time: new Date().toISOString(),
            note: 'Đơn hàng được tạo'
          }
        ],
        createdAt: new Date().toISOString()
      };

      // Lưu đơn hàng
      const orders = JSON.parse(localStorage.getItem('orders') || '[]');
      orders.push(order);
      localStorage.setItem('orders', JSON.stringify(orders));

      // Clear cart và vouchers
      localStorage.removeItem('cart');
      localStorage.removeItem('appliedVouchers');

      toast.success('Đặt hàng thành công!');
      navigate(`/order-success/${order.id}`);

    } catch (error) {
      toast.error('Có lỗi xảy ra, vui lòng thử lại');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold mb-8">Thanh toán</h1>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Form Section */}
        <div>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <h2 className="text-xl font-semibold mb-4">Thông tin giao hàng</h2>
              <div className="space-y-4">
                <div>
                  <label className="block mb-1">Họ tên</label>
                  <input
                    type="text"
                    name="fullName"
                    value={formData.fullName}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                  />
                </div>

                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block mb-1">Email</label>
                    <input
                      type="email"
                      name="email"
                      value={formData.email}
                      onChange={handleInputChange}
                      className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                    />
                  </div>
                  <div>
                    <label className="block mb-1">Số điện thoại</label>
                    <input
                      type="tel"
                      name="phone"
                      value={formData.phone}
                      onChange={handleInputChange}
                      className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                    />
                  </div>
                </div>

                <div>
                  <label className="block mb-1">Địa chỉ</label>
                  <input
                    type="text"
                    name="address"
                    value={formData.address}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                  />
                </div>

                <div className="grid grid-cols-3 gap-4">
                  <div>
                    <label className="block mb-1">Tỉnh/Thành</label>
                    <select
                      name="province"
                      value={selectedProvince}
                      onChange={(e) => setSelectedProvince(e.target.value)}
                      className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                    >
                      <option value="">Chọn Tỉnh/Thành</option>
                      {provinces.map(province => (
                        <option key={province.code} value={province.code}>
                          {province.name}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div>
                    <label className="block mb-1">Quận/Huyện</label>
                    <select
                      name="district"
                      value={selectedDistrict}
                      onChange={(e) => setSelectedDistrict(e.target.value)}
                      className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                      disabled={!selectedProvince}
                    >
                      <option value="">Chọn Quận/Huyện</option>
                      {districts.map(district => (
                        <option key={district.code} value={district.code}>
                          {district.name}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div>
                    <label className="block mb-1">Phường/Xã</label>
                    <select
                      name="ward"
                      value={formData.ward}
                      onChange={handleInputChange}
                      className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                      disabled={!selectedDistrict}
                    >
                      <option value="">Chọn Phường/Xã</option>
                      {wards.map(ward => (
                        <option key={ward.code} value={ward.code}>
                          {ward.name}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>

                <div>
                  <label className="block mb-1">Ghi chú</label>
                  <textarea
                    name="note"
                    value={formData.note}
                    onChange={handleInputChange}
                    rows="3"
                    className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                  />
                </div>
              </div>
            </div>

            <div>
              <h2 className="text-xl font-semibold mb-4">Phương thức thanh toán</h2>
              <div className="space-y-3">
                <label className="flex items-center gap-3 p-4 border rounded-lg cursor-pointer">
                  <input
                    type="radio"
                    name="paymentMethod"
                    value="cod"
                    checked={formData.paymentMethod === 'cod'}
                    onChange={handleInputChange}
                  />
                  <span>Thanh toán khi nhận hàng (COD)</span>
                </label>

                <label className="flex items-center gap-3 p-4 border rounded-lg cursor-pointer">
                  <input
                    type="radio"
                    name="paymentMethod"
                    value="banking"
                    checked={formData.paymentMethod === 'banking'}
                    onChange={handleInputChange}
                  />
                  <span>Chuyển khoản ngân hàng</span>
                </label>

                <label className="flex items-center gap-3 p-4 border rounded-lg cursor-pointer">
                  <input
                    type="radio"
                    name="paymentMethod"
                    value="momo"
                    checked={formData.paymentMethod === 'momo'}
                    onChange={handleInputChange}
                  />
                  <span>Ví MoMo</span>
                </label>
              </div>
            </div>

            <button
              type="submit"
              disabled={loading}
              className={`w-full py-4 rounded-full text-white
                ${loading 
                  ? 'bg-gray-400 cursor-not-allowed' 
                  : 'bg-black hover:bg-gray-800'}`}
            >
              {loading ? 'Đang xử lý...' : 'Đặt hàng'}
            </button>
          </form>
        </div>

        {/* Order Summary Section */}
        <div>
          <div className="bg-gray-50 p-6 rounded-lg">
            <h2 className="text-xl font-semibold mb-4">Đơn hàng của bạn</h2>
            
            {/* Cart Items */}
            <div className="space-y-4 mb-6">
              {cartItems.map((item) => (
                <div key={`${item.productId}-${item.color}-${item.size}`} 
                     className="flex gap-4">
                  <img
                    src={item.image}
                    alt={item.name}
                    className="w-20 h-20 object-cover rounded"
                  />
                  <div className="flex-grow">
                    <h3 className="font-medium">{item.name}</h3>
                    <p className="text-sm text-gray-600">
                      {item.color} | Size {item.size} | SL: {item.quantity}
                    </p>
                    <p className="font-medium">
                      {(item.price * item.quantity).toLocaleString('vi-VN')}₫
                    </p>
                  </div>
                </div>
              ))}
            </div>

            {/* Price Summary */}
            <div className="space-y-2 text-sm">
              <div className="flex justify-between">
                <span>Tạm tính</span>
                <span>{subtotal.toLocaleString('vi-VN')}₫</span>
              </div>
              <div className="flex justify-between">
                <span>Phí vận chuyển</span>
                <span>{shippingFee.toLocaleString('vi-VN')}₫</span>
              </div>
              <div className="flex justify-between font-medium text-lg pt-2 border-t">
                <span>Tổng cộng</span>
                <span>{total.toLocaleString('vi-VN')}₫</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage; 