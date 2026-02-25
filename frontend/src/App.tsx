import { Routes, Route, Link } from "react-router-dom";

import RawMaterialsPage from "./pages/RawMaterialsPage";
import ProductsPage from "./pages/ProductsPage";
import ProductionPage from "./pages/ProductionPage";

function App() {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-6xl mx-auto px-6 py-4 flex justify-between items-center">
          <h1 className="text-xl font-bold text-gray-800">
            Production Management
          </h1>

          <nav className="flex gap-6 text-gray-600 font-medium">
            <Link to="/" className="hover:text-blue-600 transition">
              Raw Materials
            </Link>
            <Link to="/products" className="hover:text-blue-600 transition">
              Products
            </Link>
            <Link to="/production" className="hover:text-blue-600 transition">
              Production
            </Link>
          </nav>
        </div>
      </header>

      <main className="flex-1 max-w-6xl mx-auto w-full p-6">
        <Routes>
          <Route path="/" element={<RawMaterialsPage />} />
          <Route path="/products" element={<ProductsPage />} />
          <Route path="/production" element={<ProductionPage />} />
        </Routes>
      </main>

      <footer className="bg-white border-t py-4 text-center text-sm text-gray-500">
        © {new Date().getFullYear()} Production System
      </footer>
    </div>
  );
}

export default App;