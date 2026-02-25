import { Link } from "react-router-dom";

const Sidebar: React.FC = () => {
  return (
    <aside className="w-64 bg-white border-r min-h-screen p-6 space-y-6">
      <h2 className="text-lg font-semibold text-gray-700">
        Navigation
      </h2>

      <nav className="flex flex-col gap-3 text-gray-600">
        <Link className="hover:text-blue-600 transition" to="/inventory">
          Inventory
        </Link>
        <Link className="hover:text-blue-600 transition" to="/production">
          Production
        </Link>
        <Link className="hover:text-blue-600 transition" to="/dashboard">
          Dashboard
        </Link>
        <Link className="hover:text-blue-600 transition" to="/raw-materials">
          Raw Materials
        </Link>
      </nav>
    </aside>
  );
};

export default Sidebar;