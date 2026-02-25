import React from "react";

import ProductionDashboard from "../components/Production/ProductionDashboard";

const ProductionPage: React.FC = () => {
  return (
    <div className="bg-white rounded-xl shadow-sm border p-6 space-y-6">
      <h1 className="text-2xl font-bold text-gray-800">
    Production Plan
      </h1>

      <ProductionDashboard />
    </div>
  );
};

export default ProductionPage;