import React, { useEffect } from "react";

import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { fetchProductionSuggestions } from "../../store/slices/productionSlice";

const ProductionDashboard: React.FC = () => {
  const dispatch = useAppDispatch();
  const { items, loading } = useAppSelector((state) => state.production);

  useEffect(() => {
    dispatch(fetchProductionSuggestions());
  }, [dispatch]);

  return (
    <div className="bg-white rounded-xl shadow-sm border p-6">
      <h2 className="text-xl font-bold mb-4 text-gray-800">
      Production Suggestions
      </h2>

      {loading && <p className="text-gray-500">Loading...</p>}

      <div className="overflow-x-auto">
        <table className="w-full border-collapse">
          <thead>
            <tr className="bg-gray-100 text-left text-sm text-gray-600">
              <th className="p-3">Product</th>
              <th className="p-3">Quantity</th>
              <th className="p-3">Unit Price</th>
              <th className="p-3">Total Value</th>
            </tr>
          </thead>
          <tbody>
            {items.map((item) => (
              <tr
                key={item.productId}
                className="border-t hover:bg-gray-50 transition"
              >
                <td className="p-3">{item.productName}</td>
                <td className="p-3">{item.quantityProduced}</td>
                <td className="p-3">${item.unitPrice}</td>
                <td className="p-3 font-semibold text-green-600">
                ${item.totalValue}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ProductionDashboard;