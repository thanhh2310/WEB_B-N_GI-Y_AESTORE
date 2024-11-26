import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const AdminProductPage = () => {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');
  const [products] = useState([
    {
      id: '4',
      name: 'Glay Response Super', 
      brand: 'Adidas',
      image: '/images/products/product-1.jpg',
      price: '$XX',
      qty: 20,
      featured: true
    },
    {
      id: '3',
      name: 'Glay Response Super',
      brand: 'Adidas', 
      image: '/product-1.jpg',
      price: '$XX',
      qty: 20,
      featured: true
    },
    {
      id: '2',
      name: 'Glay Response Super',
      brand: 'Adidas',
      image: '/product-1.jpg',
      price: '$XX',
      qty: 20,
      featured: true
    },
    {
      id: '1',
      name: 'Glay Response Super',
      brand: 'Adidas',
      image: '/product-1.jpg',
      price: '$XX',
      qty: 20,
      featured: true
    }
  ]);

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <div className="text-sm text-gray-500">
          view,crate,update,delete and manage
        </div>
        <button className="px-4 py-2 bg-black text-white rounded-md hover:bg-gray-800">
          Create
        </button>
      </div>

      {/* Search Bar */}
      <div className="bg-white rounded-lg shadow mb-6">
        <div className="p-4">
          <div className="flex gap-4">
            <input
              type="text"
              placeholder="Tìm kiếm"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="flex-1 px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-black"
            />
            <select 
              defaultValue="This week"
              className="px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-black"
            >
              <option>This week</option>
              <option>Last week</option>
              <option>This month</option>
              <option>Last month</option>
            </select>
            <select 
              defaultValue="Anytime"
              className="px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-black"
            >
              <option>Anytime</option>
              <option>Morning</option>
              <option>Afternoon</option>
              <option>Evening</option>
            </select>
          </div>
        </div>
      </div>

      {/* Products Table */}
      <div className="bg-white rounded-lg shadow">
        <div className="p-4">
          <table className="w-full">
            <thead>
              <tr className="border-b">
                <th className="text-left py-4">ID</th>
                <th className="text-left py-4">Name / Brand</th>
                <th className="text-left py-4">Price</th>
                <th className="text-left py-4">Qty</th>
                <th className="text-left py-4">Featured</th>
                <th className="text-left py-4">Actions</th>
              </tr>
            </thead>
            <tbody>
              {products.map((product) => (
                <tr key={product.id} className="border-b">
                  <td className="py-4">{product.id}</td>
                  <td className="py-4">
                    <div className="flex items-center gap-3">
                      <img 
                        src={product.image} 
                        alt={product.name}
                        className="w-12 h-12 object-cover"
                      />
                      <div>
                        <div>{product.name}</div>
                        <div className="text-gray-500">{product.brand}</div>
                      </div>
                    </div>
                  </td>
                  <td className="py-4">{product.price}</td>
                  <td className="py-4">{product.qty}</td>
                  <td className="py-4">
                    <span className="px-2 py-1 bg-teal-100 text-teal-800 rounded-full text-sm">
                      YES
                    </span>
                  </td>
                  <td className="py-4">
                    <div className="flex gap-2">
                      <button 
                        className="text-blue-600 hover:underline"
                        onClick={() => navigate(`/admin/products/${product.id}`)}
                      >
                        Details
                      </button>
                      <button className="p-1 hover:text-blue-600">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
                          <path strokeLinecap="round" strokeLinejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                        </svg>
                      </button>
                      <button className="p-1 hover:text-red-600">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
                          <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0" />
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

      {/* Pagination */}
      <div className="flex justify-end mt-4">
        <div className="flex gap-2">
          <button className="p-2 border rounded hover:bg-gray-50">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4">
              <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
            </svg>
          </button>
          <button className="px-4 py-2 border rounded bg-black text-white">
            1
          </button>
          <button className="p-2 border rounded hover:bg-gray-50">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4">
              <path strokeLinecap="round" strokeLinejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
            </svg>
          </button>
        </div>
      </div>
    </div>
  );
};

export default AdminProductPage;