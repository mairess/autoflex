import React, { useState } from "react";

import { useAppDispatch } from "../../store/hooks";
import { createRawMaterial } from "../../store/slices/rawMaterialSlice";

const RawMaterialForm: React.FC = () => {
  const dispatch = useAppDispatch();

  const [form, setForm] = useState({
    code: "",
    name: "",
    stockQuantity: 0,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    dispatch(createRawMaterial(form));
    setForm({ code: "", name: "", stockQuantity: 0 });
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
        onChange={(e) => setForm({ ...form, code: e.target.value })}
      />

      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        placeholder="Name"
        value={form.name}
        onChange={(e) => setForm({ ...form, name: e.target.value })}
      />

      <input
        type="number"
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        placeholder="Stock Quantity"
        value={form.stockQuantity}
        onChange={(e) =>
          setForm({ ...form, stockQuantity: Number(e.target.value) })
        }
      />

      <button
        type="submit"
        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition"
      >
    Create Raw Material
      </button>
    </form>
  );
};

export default RawMaterialForm;