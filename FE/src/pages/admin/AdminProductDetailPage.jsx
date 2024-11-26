import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const AdminProductDetailPage = () => {
  const navigate = useNavigate();
  const [productDetails] = useState({
    product: {
      id: 1,
      name: 'Glay Response Super',
      image: '/product-1.jpg',
      quantity: 4,
      unitPrice: '$xx',
      total: '$4xx'
    },
    supplierInfo: {
      fullName: 'Pham Nguyen',
      email: 'phamnguyen@gmail.com',
      phone: '0909090xxx',
      companyName: 'Nguyen Nguyen',
      streetAddress: 'Mon City, Thanh Xuan',
      townCity: 'Ha Noi',
      country: 'Viet Nam',
      status: 'RECEIVE ORDER'
    }
  });

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-medium">PRODUCT LIST</h1>
        <button 
          className="px-4 py-2 bg-gray-200 rounded-md hover:bg-gray-300"
          onClick={() => navigate('/admin/products')}
        >
          Back
        </button>
      </div>

      {/* Product Details */}
      <div className="bg-white rounded-lg shadow mb-6">
        <div className="p-6">
          <table className="w-full">
            <thead>
              <tr className="bg-pink-50">
                <th className="text-left py-3 px-4">Products</th>
                <th className="text-center py-3 px-4">Quantity</th>
                <th className="text-center py-3 px-4">Unit price</th>
                <th className="text-center py-3 px-4">Total</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="py-4 px-4">
                  <div className="flex items-center gap-3">
                    <img 
                      src={productDetails.product.image} 
                      alt={productDetails.product.name}
                      className="w-16 h-16 object-cover"
                    />
                    <span>{productDetails.product.name}</span>
                  </div>
                </td>
                <td className="text-center py-4 px-4">{productDetails.product.quantity}</td>
                <td className="text-center py-4 px-4">{productDetails.product.unitPrice}</td>
                <td className="text-center py-4 px-4">{productDetails.product.total}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      {/* Supplier Information */}
      <div className="bg-white rounded-lg shadow">
        <div className="p-6">
          <h2 className="text-xl font-medium mb-4">Supplier Information</h2>
          <div className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700">Full name:</label>
                <p>{productDetails.supplierInfo.fullName}</p>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Email:</label>
                <p>{productDetails.supplierInfo.email}</p>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Phone:</label>
              <p>{productDetails.supplierInfo.phone}</p>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Company Name:</label>
              <p>{productDetails.supplierInfo.companyName}</p>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Street Address:</label>
              <p>{productDetails.supplierInfo.streetAddress}</p>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Town/City:</label>
              <p>{productDetails.supplierInfo.townCity}</p>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Country:</label>
              <p>{productDetails.supplierInfo.country}</p>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Status:</label>
              <span className="inline-block px-3 py-1 bg-gray-700 text-white rounded-md text-sm mt-1">
                {productDetails.supplierInfo.status}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminProductDetailPage; 