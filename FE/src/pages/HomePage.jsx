import { useState } from 'react';
import ProductCard from '../components/ProductCard';
import Sidebar from '../components/Sidebar';

const HomePage = () => {
  const [products] = useState([
    {
      id: 1,
      name: 'Nike V2K Run',
      category: 'Shoes',
      price: 3519000,
      image: '/images/products/v2k-run.jpg',
      isNew: true
    },
    {
      id: 2,
      name: 'Nike Air Force 1 07',
      category: 'Men\'s Shoes',
      price: 2929000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/e6da41fa-1be4-4ce5-b89c-22be4f1f02d4/air-force-1-07-shoes-WrLlWX.png',
      isNew: false
    },
    {
      id: 3,
      name: 'Nike Zoom Vomero 5',
      category: 'Women\'s Shoes',
      price: 4699000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/fd17b420-b388-4c8a-aaaa-e086a6819b9f/zoom-vomero-5-shoes-qZG4RJ.png',
      isNew: true
    },
    {
      id: 4,
      name: 'Nike Air Max Plus',
      category: 'Men\'s Shoes',
      price: 4999000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/8a042953-9d71-4a8b-9b15-4c06f1bf4525/air-max-plus-shoes-pFKxz0.png',
      isNew: true
    },
    {
      id: 5,
      name: 'Nike Dunk Low',
      category: 'Women\'s Shoes',
      price: 2929000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/w2ldynwtyuspv6r5rffj/dunk-low-shoes-N9Vjbn.png',
      isNew: false
    },
    {
      id: 6,
      name: 'Nike Air Max 90',
      category: 'Men\'s Shoes',
      price: 3519000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/irwu1ye6gg6ieebhgpbe/air-max-90-shoes-kRsBnD.png',
      isNew: true
    },
    {
      id: 7,
      name: 'Nike Cortez',
      category: 'Women\'s Shoes',
      price: 2499000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/389b709e-5102-4e55-aa5d-07099b500831/cortez-shoes-0VH7J0.png',
      isNew: false
    },
    {
      id: 8,
      name: 'Nike Air Jordan 1 Mid',
      category: 'Men\'s Shoes',
      price: 3669000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/bc96b3f1-64a6-4d88-93aa-8d45c0d4a5b4/air-jordan-1-mid-shoes-SQf7DM.png',
      isNew: true
    },
    {
      id: 9,
      name: 'Nike Revolution 6',
      category: 'Running Shoes',
      price: 1909000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/8b8f4b33-af58-4888-b291-ae7726b57d23/revolution-6-road-running-shoes-NC0P7k.png',
      isNew: false
    }
  ]);

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-2xl">New (766)</h1>
        <div className="flex gap-4">
          <button className="flex items-center gap-2">
            Hide Filters
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
              <path strokeLinecap="round" strokeLinejoin="round" d="M6 13.5V3.75m0 9.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 3.75V16.5m12-3V3.75m0 9.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 3.75V16.5m-6-9V3.75m0 3.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 9.75V10.5" />
            </svg>
          </button>
          <button className="flex items-center gap-2">
            Sort By
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
              <path strokeLinecap="round" strokeLinejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5" />
            </svg>
          </button>
        </div>
      </div>

      <div className="flex gap-8">
        <Sidebar />
        <div className="flex-1">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {products.map(product => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage; 