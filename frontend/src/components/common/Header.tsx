import { Link } from "react-router-dom";

const Header: React.FC = () => {
  return (
    <header className="bg-white shadow-sm border-b">
      <div className="max-w-6xl mx-auto px-6 py-4 flex justify-between items-center">
        <h1 className="text-xl font-bold text-gray-800">
          Inventory & Production
        </h1>

        <nav className="flex gap-6 text-gray-600 font-medium">
          <Link to="/" className="hover:text-blue-600 transition">
            Dashboard
          </Link>
          <Link to="/inventory" className="hover:text-blue-600 transition">
            Inventory
          </Link>
          <Link to="/production" className="hover:text-blue-600 transition">
            Production
          </Link>
          <Link to="/raw-materials" className="hover:text-blue-600 transition">
            Raw Materials
          </Link>
        </nav>
      </div>
    </header>
  );
};

export default Header;