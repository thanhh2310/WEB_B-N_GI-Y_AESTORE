import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-hot-toast';

const AdminProductDetailPage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [productDetails, setProductDetails] = useState([]);
  const [loading, setLoading] = useState(true);

  // Fetch product details
  const fetchProductDetails = async () => {
    try {
      setLoading(true);
      // Sửa lại endpoint theo ProductDetailController
      const response = await axios.get(`http://localhost:8081/saleShoes/productdetails/${id}`);
      
      if (response.data?.result) {
        // Đảm bảo dữ liệu trả về là mảng
        const details = Array.isArray(response.data.result) ? response.data.result : [];
        setProductDetails(details);
      } else {
        setProductDetails([]);
      }
    } catch (error) {
      console.error('Error fetching product details:', error);
      toast.error('Không thể tải chi tiết sản phẩm');
      setProductDetails([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (id) {
      fetchProductDetails();
    }
  }, [id]);

  // Toggle variant status
  const toggleVariantStatus = async (variantId) => {
    try {
      const variant = productDetails.find(v => v.id === variantId);
      if (!variant) return;

      const updatedVariant = {
        productId: variant.product?.id,
        colorId: variant.color?.id,
        sizeId: variant.size?.id,
        quantity: variant.quantity,
        active: !variant.active
      };

      await axios.patch(`http://localhost:8081/saleShoes/productdetails/${variantId}`, updatedVariant);
      
      setProductDetails(productDetails.map(v => 
        v.id === variantId 
          ? { ...v, active: !v.active }
          : v
      ));

      toast.success('Cập nhật trạng thái thành công');
    } catch (error) {
      console.error('Error toggling variant status:', error);
      toast.error('Không thể cập nhật trạng thái');
    }
  };

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

      {/* Product Variants Table */}
      <div className="bg-white rounded-lg shadow">
        <div className="p-6">
          {productDetails.length === 0 ? (
            <div className="text-center py-4 text-gray-500">
              Không có biến thể sản phẩm nào
            </div>
          ) : (
            <table className="w-full">
              <thead>
                <tr className="bg-gray-50">
                  <th className="text-left py-3 px-4">ID</th>
                  <th className="text-left py-3 px-4">Color</th>
                  <th className="text-left py-3 px-4">Size</th>
                  <th className="text-center py-3 px-4">Quantity</th>
                  <th className="text-center py-3 px-4">Status</th>
                  <th className="text-center py-3 px-4">Actions</th>
                </tr>
              </thead>
              <tbody>
                {productDetails.map((variant) => (
                  <tr key={variant.id} className="border-b">
                    <td className="py-4 px-4">{variant.id}</td>
                    <td className="py-4 px-4">{variant.color?.name}</td>
                    <td className="py-4 px-4">{variant.size?.name}</td>
                    <td className="text-center py-4 px-4">{variant.quantity}</td>
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
                            // Handle edit variant
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
          )}
        </div>
      </div>
    </div>
  );
};

export default AdminProductDetailPage; 