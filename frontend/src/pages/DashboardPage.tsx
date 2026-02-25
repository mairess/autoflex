import React from "react";

import ProductionDashboard from "../components/Production/ProductionDashboard";

const DashboardPage: React.FC = () => {

  return (
    <div className="bg-white rounded-xl shadow-sm border p-6 space-y-8">
      <h1 className="text-2xl font-bold text-gray-800">
    Dashboard
      </h1>

      <section className="space-y-4">
        <h2 className="text-lg font-semibold text-gray-700">
      Production Overview
        </h2>
        <ProductionDashboard />
      </section>

      <section className="space-y-4">
        <h2 className="text-lg font-semibold text-gray-700">
      Production Suggestions
        </h2>
        <ProductionDashboard />
      </section>
    </div>
  );
};

export default DashboardPage;