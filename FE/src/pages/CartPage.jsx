import { Link } from 'react-router-dom';
import { useState } from 'react';

const CartPage = () => {
  const [recommendedProducts] = useState([
    {
      id: 1,
      name: 'Nike Air Max Dn',
      category: "Older Kids' Shoes",
      price: 2929000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/5671b1bf-3e7f-4f61-a356-7b434205a89f_nike_air_max_dn.jpg'
    },
    {
      id: 2,
      name: 'Nike Invincible 3',
      category: "Men's Road Running Shoes",
      price: 3519000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/fd17b420-b388-4c8a-aaaa-e086a6819b9f/invincible-3-road-running-shoes.png'
    },
    {
      id: 3,
      name: 'Nike Unicorn',
      category: 'Dri-FIT ADV Cushioned Crew Socks (1 Pair)',
      price: 489000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/389b709e-5102-4e55-aa5d-07099b500831/unicorn-socks.png'
    },
    {
      id: 4,
      name: 'Nike Air Max Plus',
      category: "Men's Shoes",
      price: 4999000,
      image: 'https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/8a042953-9d71-4a8b-9b15-4c06f1bf4525/air-max-plus-shoes.png'
    }
  ]);
  const [voucherCode, setVoucherCode] = useState('');
  const [appliedVouchers, setAppliedVouchers] = useState([]);
  const [voucherError, setVoucherError] = useState('');

  // Mock voucher data - in real app, this would come from API
  const availableVouchers = [
    { code: 'NIKE10', discount: 100000, description: 'Giảm 100.000₫' },
    { code: 'NIKE20', discount: 200000, description: 'Giảm 200.000₫' },
    { code: 'FREESHIP', discount: 50000, description: 'Freeship' },
  ];

  const handleApplyVoucher = () => {
    // Reset error
    setVoucherError('');

    // Check if voucher already applied
    if (appliedVouchers.some(v => v.code === voucherCode)) {
      setVoucherError('Voucher đã được sử dụng');
      return;
    }

    // Find voucher in available vouchers
    const voucher = availableVouchers.find(v => v.code === voucherCode);
    if (!voucher) {
      setVoucherError('Voucher không hợp lệ');
      return;
    }

    // Add voucher to applied vouchers
    setAppliedVouchers([...appliedVouchers, voucher]);
    setVoucherCode(''); // Clear input
  };

  const removeVoucher = (voucherToRemove) => {
    setAppliedVouchers(appliedVouchers.filter(v => v.code !== voucherToRemove.code));
  };

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Announcement Banner */}
      <div className="bg-gray-100 p-4 mb-8 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <span className="font-medium">FREE DELIVERY</span>
          <span>Applies to orders of 5,000,000₫ or more.</span>
          <Link to="/details" className="underline">View details.</Link>
        </div>
      </div>

      <div className="flex flex-col lg:flex-row gap-8 mb-12">
        {/* Cart Section */}
        <div className="flex-1">
          <h1 className="text-2xl font-medium mb-6">Bag</h1>
          <div className="mb-6">
            <p className="text-lg">There are no items in your bag.</p>
          </div>

          {/* Favourites Section */}
          <div className="mt-12">
            <h2 className="text-2xl font-medium mb-4">Favourites</h2>
            <div>
              <p>Want to view your favourites?</p>
              <div className="flex gap-2 mt-2">
                <Link to="/join" className="underline">Join us</Link>
                <span>or</span>
                <Link to="/signin" className="underline">Sign in</Link>
              </div>
            </div>
          </div>
        </div>

        {/* Summary Section */}
        <div className="lg:w-96">
          <div className="bg-white p-6">
            <h2 className="text-2xl font-medium mb-6">Summary</h2>
            
            {/* Subtotal */}
            <div className="flex justify-between mb-4">
              <div className="flex items-center gap-1">
                <span>Subtotal</span>
                <button className="ml-1">
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4">
                    <path strokeLinecap="round" strokeLinejoin="round" d="m11.25 11.25.041-.02a.75.75 0 0 1 1.063.852l-.708 2.836a.75.75 0 0 0 1.063.853l.041-.021M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9-3.75h.008v.008H12V8.25Z" />
                  </svg>
                </button>
              </div>
              <span>—</span>
            </div>

            {/* Delivery & Handling */}
            <div className="flex justify-between mb-4">
              <span>Estimated Delivery & Handling</span>
              <span>Free</span>
            </div>

            {/* Voucher Section */}
            <div className="mb-4">
              <div className="flex gap-2">
                <input
                  type="text"
                  placeholder="Enter Voucher Code"
                  value={voucherCode}
                  onChange={(e) => setVoucherCode(e.target.value.toUpperCase())}
                  className="flex-1 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-black"
                />
                <button
                  onClick={handleApplyVoucher}
                  className="px-4 py-2 bg-black text-white rounded-md hover:bg-gray-800 disabled:bg-gray-300"
                  disabled={!voucherCode}
                >
                  Apply
                </button>
              </div>
              {voucherError && (
                <p className="text-red-500 text-sm mt-1">{voucherError}</p>
              )}
            </div>

            {/* Applied Vouchers */}
            {appliedVouchers.length > 0 && (
              <div className="mb-4 space-y-2">
                <h3 className="font-medium text-sm">Applied Vouchers:</h3>
                {appliedVouchers.map((voucher) => (
                  <div key={voucher.code} className="flex justify-between items-center bg-gray-50 p-2 rounded">
                    <div>
                      <p className="font-medium text-sm">{voucher.code}</p>
                      <p className="text-xs text-gray-600">{voucher.description}</p>
                    </div>
                    <button
                      onClick={() => removeVoucher(voucher)}
                      className="text-gray-500 hover:text-black"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                      </svg>
                    </button>
                  </div>
                ))}
              </div>
            )}

            {/* Voucher Discounts */}
            {appliedVouchers.map((voucher) => (
              <div key={voucher.code} className="flex justify-between mb-2 text-sm">
                <span>Discount ({voucher.code})</span>
                <span className="text-green-600">-{voucher.discount.toLocaleString()}₫</span>
              </div>
            ))}

            {/* Total */}
            <div className="flex justify-between py-4 border-t border-gray-200">
              <span>Total</span>
              <span>—</span>
            </div>

            {/* Checkout Buttons */}
            <div className="space-y-4 mt-6">
              <button className="w-full bg-gray-100 text-gray-400 py-4 rounded-full cursor-not-allowed">
                Guest Checkout
              </button>
              <button className="w-full bg-gray-100 text-gray-400 py-4 rounded-full cursor-not-allowed">
                Member Checkout
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* You Might Also Like Section */}
      <div className="mt-16">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl">You Might Also Like</h2>
          <div className="flex gap-2">
            <button className="p-2 rounded-full border hover:border-black">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
                <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
              </svg>
            </button>
            <button className="p-2 rounded-full border hover:border-black">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
                <path strokeLinecap="round" strokeLinejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
              </svg>
            </button>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
          {recommendedProducts.map((product) => (
            <Link key={product.id} to={`/product/${product.id}`} className="group">
              <div className="aspect-square overflow-hidden">
                <img 
                  src={product.image} 
                  alt={product.name}
                  className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
                />
              </div>
              <div className="mt-4">
                <h3 className="font-medium">{product.name}</h3>
                <p className="text-gray-600">{product.category}</p>
                <p className="mt-1">{product.price.toLocaleString()}₫</p>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
};

export default CartPage; 