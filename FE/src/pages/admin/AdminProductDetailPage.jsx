import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-hot-toast';

const AdminProductDetailPage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [variantsByColor, setVariantsByColor] = useState({});
  const [loading, setLoading] = useState(true);
  const [showEditModal, setShowEditModal] = useState(false);
  const [editingVariant, setEditingVariant] = useState(null);

  // Fetch product variants grouped by color
  const fetchProductVariants = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`http://localhost:8081/saleShoes/productdetails/product/${id}/variants`);
      
      if (response.data?.result) {
        setVariantsByColor(response.data.result);
      }
    } catch (error) {
      console.error('Error fetching product variants:', error);
      toast.error('Không thể tải chi tiết sản phẩm');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (id) {
      fetchProductVariants();
    }
  }, [id]);

  // Toggle variant status
  const toggleVariantStatus = async (variantId) => {
    try {
      const variant = Object.values(variantsByColor)
        .flat()
        .find(v => v.id === variantId);
      
      if (!variant) return;

      const updatedVariant = {
        productId: variant.productId,
        color: variant.color,
        size: variant.size,
        quantity: variant.quantity,
        price: variant.price,
        description: variant.description,
        active: !variant.active
      };

      await axios.patch(`http://localhost:8081/saleShoes/productdetails/${variantId}`, updatedVariant);
      
      // Cập nhật state local
      setVariantsByColor(prev => {
        const newState = { ...prev };
        Object.keys(newState).forEach(color => {
          newState[color] = newState[color].map(v => 
            v.id === variantId ? { ...v, active: !v.active } : v
          );
        });
        return newState;
      });

      toast.success('Cập nhật trạng thái thành công');
    } catch (error) {
      console.error('Error toggling variant status:', error);
      toast.error('Không thể cập nhật trạng thái');
    }
  };

  // Edit Modal Component
  const EditModal = () => {
    const [editForm, setEditForm] = useState({
      price: editingVariant?.price || 0,
      quantity: editingVariant?.quantity || 0
    });

    const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        const updatedVariant = {
          productId: editingVariant.productId,
          color: editingVariant.color,
          size: editingVariant.size,
          quantity: editForm.quantity,
          price: editForm.price,
          description: editingVariant.description,
          active: editingVariant.active
        };

        await axios.patch(
          `http://localhost:8081/saleShoes/productdetails/${editingVariant.id}`, 
          updatedVariant
        );

        // Cập nhật state local
        setVariantsByColor(prev => {
          const newState = { ...prev };
          Object.keys(newState).forEach(color => {
            newState[color] = newState[color].map(v => 
              v.id === editingVariant.id 
                ? { ...v, price: editForm.price, quantity: editForm.quantity }
                : v
            );
          });
          return newState;
        });

        toast.success('Cập nhật thành công');
        setShowEditModal(false);
      } catch (error) {
        console.error('Error updating variant:', error);
        toast.error('Không thể cập nhật biến thể');
      }
    };

    return (
      <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
        <div className="bg-white p-6 rounded-lg w-96">
          <h2 className="text-xl font-bold mb-4">Cập nhật biến thể</h2>
          <form onSubmit={handleSubmit}>
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700">
                  Màu sắc
                </label>
                <input
                  type="text"
                  value={editingVariant?.color}
                  disabled
                  className="mt-1 px-4 py-2 w-full border rounded-md bg-gray-50"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">
                  Kích thước
                </label>
                <input
                  type="text"
                  value={editingVariant?.size}
                  disabled
                  className="mt-1 px-4 py-2 w-full border rounded-md bg-gray-50"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">
                  Số lượng
                </label>
                <input
                  type="number"
                  value={editForm.quantity}
                  onChange={(e) => setEditForm({ ...editForm, quantity: parseInt(e.target.value) })}
                  min="0"
                  className="mt-1 px-4 py-2 w-full border rounded-md"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">
                  Giá
                </label>
                <input
                  type="number"
                  value={editForm.price}
                  onChange={(e) => setEditForm({ ...editForm, price: parseFloat(e.target.value) })}
                  min="0"
                  className="mt-1 px-4 py-2 w-full border rounded-md"
                  required
                />
              </div>
            </div>
            <div className="flex justify-end gap-2 mt-6">
              <button
                type="button"
                onClick={() => setShowEditModal(false)}
                className="px-4 py-2 border rounded-md"
              >
                Hủy
              </button>
              <button
                type="submit"
                className="px-4 py-2 bg-black text-white rounded-md"
              >
                Cập nhật
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  };

  // Thêm loading state handler
  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-gray-900"></div>
      </div>
    );
  }

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-medium">PRODUCT VARIANTS</h1>
        <button 
          className="px-4 py-2 bg-gray-200 rounded-md hover:bg-gray-300"
          onClick={() => navigate('/admin/products')}
        >
          Back
        </button>
      </div>

      {/* Product Variants By Color */}
      {Object.entries(variantsByColor).map(([color, variants]) => (
        <div key={color} className="bg-white rounded-lg shadow mb-6">
          <div className="p-6">
            <h2 className="text-xl font-semibold mb-4">Màu: {color}</h2>
            <table className="w-full">
              <thead>
                <tr className="bg-gray-50">
                  <th className="text-left py-3 px-4">Size</th>
                  <th className="text-center py-3 px-4">Quantity</th>
                  <th className="text-center py-3 px-4">Price</th>
                  <th className="text-center py-3 px-4">Status</th>
                  <th className="text-center py-3 px-4">Actions</th>
                </tr>
              </thead>
              <tbody>
                {variants.map((variant) => (
                  <tr key={variant.id} className="border-b">
                    <td className="py-4 px-4">{variant.size}</td>
                    <td className="text-center py-4 px-4">{variant.quantity}</td>
                    <td className="text-center py-4 px-4">
                      {variant.price?.toLocaleString('vi-VN')}đ
                    </td>
                    <td className="py-4 px-4">
                      <div className="flex justify-center">
                        <button
                          onClick={() => toggleVariantStatus(variant.id)}
                          className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors focus:outline-none ${
                            variant.active ? 'bg-green-500' : 'bg-gray-300'
                          }`}
                        >
                          <span
                            className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                              variant.active ? 'translate-x-6' : 'translate-x-1'
                            }`}
                          />
                        </button>
                      </div>
                    </td>
                    <td className="py-4 px-4">
                      <div className="flex justify-center gap-2">
                        <button 
                          className="p-1 hover:text-blue-600"
                          onClick={() => {
                            setEditingVariant(variant);
                            setShowEditModal(true);
                          }}
                        >
                          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
                            <path strokeLinecap="round" strokeLinejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                          </svg>
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      ))}

      {/* Modal */}
      {showEditModal && <EditModal />}
    </div>
  );
};

export default AdminProductDetailPage; 