import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-hot-toast';

const AdminOrderPage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [timeFilter, setTimeFilter] = useState('This week');
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  // Fetch orders when component mounts
  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      const token = localStorage.getItem('token');
      // Gọi API lấy tất cả đơn hàng
      const response = await axios.get('http://localhost:8081/saleShoes/orders/all', {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (response.data?.result) {
        setOrders(response.data.result);
      }
    } catch (error) {
      console.error('Error fetching orders:', error);
      toast.error('Không thể tải danh sách đơn hàng');
    } finally {
      setLoading(false);
    }
  };

  // Hàm cập nhật trạng thái đơn hàng
  const updateOrderStatus = async (orderId, newStatus) => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.patch(
        `http://localhost:8081/saleShoes/orders/updateStatus?orderId=${orderId}&newStatus=${newStatus}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` }
        }
      );

      if (response.data?.result) {
        // Cập nhật lại danh sách đơn hàng
        setOrders(orders.map(order => 
          order.id === orderId 
            ? { ...order, status: newStatus }
            : order
        ));
        toast.success('Cập nhật trạng thái đơn hàng thành công');
      }
    } catch (error) {
      console.error('Error updating order status:', error);
      toast.error('Không thể cập nhật trạng thái đơn hàng');
    }
  };

  // Lọc đơn hàng theo search query
  const filteredOrders = orders.filter(order => {
    const searchString = searchQuery.toLowerCase();
    return (
      order.id.toString().includes(searchString) ||
      order.address.toLowerCase().includes(searchString)
    );
  });

  // Hàm format trạng thái đơn hàng
  const getStatusColor = (status) => {
    switch (status) {
      case 'CHOXACNHAN':
        return 'bg-yellow-100 text-yellow-800';
      case 'XACNHAN':
        return 'bg-blue-100 text-blue-800';
      case 'GIAOHANG':
        return 'bg-purple-100 text-purple-800';
      case 'HOANTHANH':
        return 'bg-green-100 text-green-800';
      case 'HUYDONHANG':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  const getStatusText = (status) => {
    switch (status) {
      case 'CHOXACNHAN':
        return 'Chờ xác nhận';
      case 'XACNHAN':
        return 'Xác nhận';
      case 'GIAOHANG':
        return 'Giao hàng';
      case 'HOANTHANH':
        return 'Hoàn thành';
      case 'HUYDONHANG':
        return 'Huỷ đơn Hàng';
      default:
        return status;
    }
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-medium">Quản lý đơn hàng</h1>
        <div className="text-sm text-gray-500">
          Xem và quản lý đơn hàng
        </div>
      </div>

      {/* Search Bar */}
      <div className="bg-white rounded-lg shadow mb-6">
        <div className="p-4">
          <div className="flex gap-4">
            <div className="flex-1 flex gap-4">
              <input
                type="text"
                placeholder="Tìm kiếm theo mã đơn hàng hoặc địa chỉ"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="flex-1 px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-black"
              />
              <select 
                value={timeFilter}
                onChange={(e) => setTimeFilter(e.target.value)}
                className="px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-black"
              >
                <option>This week</option>
                <option>Last week</option>
                <option>This month</option>
                <option>Last month</option>
              </select>
            </div>
          </div>
        </div>
      </div>

      {/* Orders Table */}
      <div className="bg-white rounded-lg shadow">
        <div className="p-4">
          {loading ? (
            <div className="text-center py-4">Đang tải...</div>
          ) : (
            <table className="w-full">
              <thead>
                <tr className="border-b">
                  <th className="text-left py-4">ID</th>
                  <th className="text-left py-4">Sản phẩm</th>
                  <th className="text-left py-4">Địa chỉ</th>
                  <th className="text-left py-4">Tổng tiền</th>
                  <th className="text-left py-4">Trạng thái</th>
                  <th className="text-left py-4">Thao tác</th>
                </tr>
              </thead>
              <tbody>
                {filteredOrders.map((order) => (
                  <tr key={order.id} className="border-b">
                    <td className="py-4">{order.id}</td>
                    <td className="py-4">
                      <div className="space-y-2">
                        {order.orderDetail.map((item) => (
                          <div key={item.id} className="flex items-center gap-3">
                            <img 
                              src={item.productDetail.image[0]} 
                              alt=""
                              className="w-12 h-12 object-cover rounded"
                            />
                            <div>
                              <p className="font-medium">Size: {item.productDetail.size}</p>
                              <p className="text-sm text-gray-600">
                                {item.productDetail.color} | SL: {item.quantity}
                              </p>
                            </div>
                          </div>
                        ))}
                      </div>
                    </td>
                    <td className="py-4">{order.address}</td>
                    <td className="py-4">
                      {order.orderDetail.reduce((total, item) => 
                        total + (item.price * item.quantity), 0
                      ).toLocaleString('vi-VN')}₫
                    </td>
                    <td className="py-4">
                      <span className={`px-3 py-1 rounded-full text-sm ${getStatusColor(order.status)}`}>
                        {getStatusText(order.status)}
                      </span>
                    </td>
                    <td className="py-4">
                      <select
                        value={order.status}
                        onChange={(e) => updateOrderStatus(order.id, e.target.value)}
                        className="px-3 py-1 border rounded-md focus:outline-none focus:ring-1 focus:ring-black"
                        disabled={order.status === 'DAGIAO' || order.status === 'DAHUY'}
                      >
                        <option value="CHOXACNHAN">Chờ xác nhận</option>
                        <option value="XACNHAN">Xác nhận</option>
                        <option value="GIAOHANG">Giao hàng</option>
                        <option value="HOANTHANH">Hoàn thành</option>
                        <option value="HUYDONHANG">Huỷ đơn hàng</option>
                      </select>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}

          {!loading && filteredOrders.length === 0 && (
            <div className="text-center py-8 text-gray-500">
              Không tìm thấy đơn hàng nào
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default AdminOrderPage; 