import { Routes, Route } from "react-router-dom";

import Header from "./components/common/Header";
import Footer from "./components/common/Footer";
import RawMaterialsPage from "./pages/RawMaterialsPage";
import ProductsPage from "./pages/ProductsPage";
import ProductionPage from "./pages/ProductionPage";

function App() {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      <Header />

      <main className="flex-1 max-w-6xl mx-auto w-full p-6">
        <Routes>
          <Route path="/" element={<RawMaterialsPage />} />
          <Route path="/products" element={<ProductsPage />} />
          <Route path="/production" element={<ProductionPage />} />
        </Routes>
      </main>

      <Footer />
    </div>
  );
}

export default App;