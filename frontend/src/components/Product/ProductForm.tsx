import React, { useState } from "react";

import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { createProduct } from "../../store/slices/productSlice";

const ProductForm: React.FC = () => {
  const dispatch = useAppDispatch();
  const rawMaterials = useAppSelector((s) => s.rawMaterials.items);

  const [form, setForm] = useState({
    code: "",
    name: "",
    price: 0,
    rawMaterials: [] as { rawMaterialId: number; requiredQuantity: number }[],
  });

  const addMaterial = (id: number) => {
    if (form.rawMaterials.find((rm) => rm.rawMaterialId === id)) return;

    setForm({
      ...form,
      rawMaterials: [
        ...form.rawMaterials,
        { rawMaterialId: id, requiredQuantity: 1 },
      ],
    });
  };

  const updateQuantity = (id: number, quantity: number) => {
    setForm({
      ...form,
      rawMaterials: form.rawMaterials.map((rm) =>
        rm.rawMaterialId === id
          ? { ...rm, requiredQuantity: quantity }
          : rm,
      ),
    });
  };

  const removeMaterial = (id: number) => {
    setForm({
      ...form,
      rawMaterials: form.rawMaterials.filter(
        (rm) => rm.rawMaterialId !== id,
      ),
    });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    dispatch(createProduct(form));
    setForm({
      code: "",
      name: "",
      price: 0,
      rawMaterials: [],
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        placeholder="Code"
        onChange={(e) => setForm({ ...form, code: e.target.value })}
      />

      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        placeholder="Name"
        onChange={(e) => setForm({ ...form, name: e.target.value })}
      />

      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        type="number"
        placeholder="Price"
        onChange={(e) =>
          setForm({ ...form, price: Number(e.target.value) })
        }
      />

      <h4>Available Raw Materials</h4>

      {rawMaterials.map((rm) => (
        <div key={rm.id}>
          <button 
            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition"
            type="button" onClick={() => addMaterial(rm.id)}>
            Add {rm.name}
          </button>
        </div>
      ))}

      <h4>Selected Materials</h4>

      {form.rawMaterials.map((rm) => {
        const material = rawMaterials.find(
          (r) => r.id === rm.rawMaterialId,
        );

        return (
          <div key={rm.rawMaterialId}>
            <span>{material?.name}</span>

            <input
              className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
              type="number"
              value={rm.requiredQuantity}
              onChange={(e) =>
                updateQuantity(
                  rm.rawMaterialId,
                  Number(e.target.value),
                )
              }
            />

            <button
              className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition"
              type="button"
              onClick={() => removeMaterial(rm.rawMaterialId)}
            >
              Remove
            </button>
          </div>
        );
      })}

      <button 
        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition"
        type="submit">Create Product</button>
    </form>
  );
};

export default ProductForm;