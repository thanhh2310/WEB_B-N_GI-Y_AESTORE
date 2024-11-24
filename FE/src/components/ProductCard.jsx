import { Link } from 'react-router-dom';

const ProductCard = ({ product }) => {
  return (
    <Link to={`/product/${product.id}`} className="group">
      <div className="relative overflow-hidden">
        <img 
          src={product.image} 
          alt={product.name}
          className="w-full object-cover transition-transform duration-300 group-hover:scale-105"
        />
        {product.isNew && (
          <span className="absolute top-2 left-2 bg-white px-2 py-1 text-sm">
            Just In
          </span>
        )}
      </div>
      <div className="mt-4">
        <h3 className="font-medium">{product.name}</h3>
        <p className="text-nike-gray">{product.category}</p>
        <p className="mt-1">{product.price.toLocaleString()}₫</p>
      </div>
    </Link>
  );
};

export default ProductCard; 