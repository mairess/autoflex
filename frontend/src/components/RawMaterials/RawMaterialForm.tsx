import React, { useState } from "react";

import { useAppDispatch } from "../../store/hooks";
import { createRawMaterial } from "../../store/slices/rawMaterialSlice";

const RawMaterialForm: React.FC = () => {
  const dispatch = useAppDispatch();

  const [error, setError] = useState<string | null>(null);

  const [form, setForm] = useState({
    code: "",
    name: "",
    stockQuantity: 0,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!form.code.trim()) {
      setError("Code is required");
      return;
    }

    if (!form.name.trim()) {
      setError("Name is required");
      return;
    }

    if (form.stockQuantity < 0) {
      setError("Stock quantity cannot be negative");
      return;
    }

    setError(null);

    dispatch(createRawMaterial(form));

    setForm({
      code: "",
      name: "",
      stockQuantity: 0,
    });
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="space-y-4 bg-gray-50 p-4 rounded-lg border"
    >
      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        placeholder="Code"
        value={form.code}
        onChange={(e) => {
          setError(null);
          setForm({ ...form, code: e.target.value });
        }}
      />

      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        placeholder="Name"
        value={form.name}
        onChange={(e) => {
          setError(null);
          setForm({ ...form, name: e.target.value });
        }}
      />

      <input
        type="number"
        min={0}
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        placeholder="Stock Quantity"
        value={form.stockQuantity}
        onChange={(e) => {
          setError(null);
          setForm({
            ...form,
            stockQuantity: Math.max(0, Number(e.target.value)),
          });
        }}
      />

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg">
          {error}
        </div>
      )}

      <button
        type="submit"
        disabled={
          !form.code.trim() ||
          !form.name.trim() ||
          form.stockQuantity < 0
        }
        className="bg-blue-600 disabled:bg-gray-400 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition"
      >
        Create Raw Material
      </button>
    </form>
  );
};

export default RawMaterialForm;