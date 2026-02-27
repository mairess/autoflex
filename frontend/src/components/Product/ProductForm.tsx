import React, { useState } from "react";

import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { createProduct, updateProduct } from "../../store/slices/productSlice";
import type { ProductRawMaterialFormType, ProductResponseType } from "../../types/product";

interface ProductFormProps {
  initialData?: ProductResponseType;
  onFinish?: () => void;
}

const buildFormState = (data?: ProductResponseType) => ({
  code: data?.code ?? "",
  name: data?.name ?? "",
  price: data?.price ?? 0,
  rawMaterials: data
    ? data.rawMaterials.map((rm) => ({
      rawMaterialId: rm.rawMaterialId,
      requiredQuantity: rm.requiredQuantity,
    }))
    : ([] as ProductRawMaterialFormType[]),
});

const ProductForm: React.FC<ProductFormProps> = ({
  initialData,
  onFinish,
}) => {
  const dispatch = useAppDispatch();
  const rawMaterials = useAppSelector((s) => s.rawMaterials.items);
  const [error, setError] = useState<string | null>(null);
  const [search, setSearch] = useState("");
  const [form, setForm] = useState(() => buildFormState(initialData));

  const filteredMaterials = rawMaterials.filter((rm) =>
    rm.name.toLowerCase().includes(search.toLowerCase()) ||
  rm.code.toLowerCase().includes(search.toLowerCase()),
  );

  const addMaterial = (id: number) => {
    if (form.rawMaterials.find((rm) => rm.rawMaterialId === id)) return;

    setForm((prev) => ({
      ...prev,
      rawMaterials: [
        ...prev.rawMaterials,
        { rawMaterialId: id, requiredQuantity: 1 },
      ],
    }));
  };

  const updateQuantity = (id: number, quantity: number) => {
    setForm((prev) => ({
      ...prev,
      rawMaterials: prev.rawMaterials.map((rm) =>
        rm.rawMaterialId === id
          ? { ...rm, requiredQuantity: quantity }
          : rm,
      ),
    }));
  };

  const removeMaterial = (id: number) => {
    setForm((prev) => ({
      ...prev,
      rawMaterials: prev.rawMaterials.filter(
        (rm) => rm.rawMaterialId !== id,
      ),
    }));
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!form.code.trim()) {
      setError("Code is required");
      return;
    }

    if (!form.name.trim()) {
      setError("Name is required");
      return;
    }

    if (form.price <= 0) {
      setError("Price must be greater than zero");
      return;
    }

    if (form.rawMaterials.length === 0) {
      setError("At least one raw material must be selected");
      return;
    }

    setError(null);

    try {
      if (initialData) {
        await dispatch(
          updateProduct({ id: initialData.id, data: form }),
        ).unwrap();
      } else {
        await dispatch(createProduct(form)).unwrap();
      }

      setError(null);

      onFinish?.();
      setForm(buildFormState());

    } catch (error) {
      setError(String(error));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <h2 className="text-lg font-bold">
        {initialData
          ? `Editing: ${initialData.name}`
          : "Create New Product"}
      </h2>
      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        value={form.code}
        placeholder="Code"
        onChange={(e) =>
          setForm((prev) => ({ ...prev, code: e.target.value }))
        }
      />

      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        value={form.name}
        placeholder="Name"
        onChange={(e) =>
          setForm((prev) => ({ ...prev, name: e.target.value }))
        }
      />

      <input
        className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        type="number"
        min={0.01}
        step="0.01"
        placeholder="Price"
        value={form.price}
        onChange={(e) =>
          setForm((prev) => ({
            ...prev,
            price: Number(e.target.value),
          }))
        }
      />

      <h4 className="font-semibold text-gray-700 mt-4">
  Available Raw Materials
      </h4>

      <div className="space-y-3">

        <input
          type="text"
          placeholder="Search by name or code..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
        />

        <div className="max-h-48 overflow-y-auto border rounded-lg divide-y bg-white">

          {filteredMaterials.map((rm) => {
            const alreadySelected = form.rawMaterials.some(
              (m) => m.rawMaterialId === rm.id,
            );

            return (
              <div
                key={rm.id}
                className="flex justify-between items-center p-3 hover:bg-gray-50 transition"
              >
                <div>
                  <p className="text-sm font-medium text-gray-800">
                    {rm.code} - {rm.name}
                  </p>
                  <p className="text-xs text-gray-500">
              Stock: {rm.stockQuantity}
                  </p>
                </div>

                <button
                  type="button"
                  disabled={alreadySelected}
                  onClick={() => addMaterial(rm.id)}
                  className={`px-3 py-1 rounded-lg text-sm transition cursor-pointer ${
                    alreadySelected
                      ? "bg-gray-300 text-gray-600 cursor-not-allowed"
                      : "bg-blue-600 hover:bg-blue-700 text-white"
                  }`}
                >
                  {alreadySelected ? "Added" : "Add"}
                </button>
              </div>
            );
          })}

          {filteredMaterials.length === 0 && (
            <p className="p-3 text-sm text-gray-500">
        No materials found
            </p>
          )}
        </div>
      </div>

      <h4 className="font-semibold">Selected Materials</h4>

      <div className="max-h-56 overflow-y-auto border rounded-lg divide-y bg-white">
        {form.rawMaterials.length === 0 && (
          <p className="p-3 text-sm text-gray-500">
      No materials selected
          </p>
        )}

        {form.rawMaterials.map((rm) => {
          const material = rawMaterials.find(
            (r) => r.id === rm.rawMaterialId,
          );

          return (
            <div
              key={rm.rawMaterialId}
              className="flex justify-between items-center p-3"
            >
              <span className="w-40 text-sm">
                {material?.name}
              </span>

              <input
                type="number"
                min={1}
                className="border rounded-lg px-2 py-1 w-24"
                value={rm.requiredQuantity}
                onChange={(e) =>
                  updateQuantity(
                    rm.rawMaterialId,
                    Math.max(1, Number(e.target.value)),
                  )
                }
              />

              <button
                type="button"
                className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded-lg transition"
                onClick={() => removeMaterial(rm.rawMaterialId)}
              >
          Remove
              </button>
            </div>
          );
        })}
      </div>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg">
          {error}
        </div>
      )}

      <div className="flex gap-3">
        <button
          className="bg-blue-600 disabled:bg-gray-400 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition"
          type="submit"
        >
          {initialData ? "Update Product" : "Create Product"}
        </button>

        {initialData && (
          <button
            type="button"
            onClick={() => {
              setForm(buildFormState());
              onFinish?.();
            }}
            className="bg-gray-400 hover:bg-gray-500 text-white px-4 py-2 rounded-lg transition"
          >
      Cancel
          </button>
        )}
      </div>
    </form>
  );
};

export default ProductForm;