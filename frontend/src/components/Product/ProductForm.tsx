import React, { useState } from "react";

import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { createProduct, updateProduct } from "../../store/slices/productSlice";
import type {
  ProductRawMaterialFormType,
  ProductResponseType,
} from "../../types/product";

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

  const [form, setForm] = useState(() => buildFormState(initialData));

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

      <h4 className="font-semibold">Available Raw Materials</h4>

      {rawMaterials.map((rm) => (
        <div key={rm.id}>
          <button
            type="button"
            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition"
            onClick={() => addMaterial(rm.id)}
          >
            Add {rm.name}
          </button>
        </div>
      ))}

      <h4 className="font-semibold">Selected Materials</h4>

      {form.rawMaterials.map((rm) => {
        const material = rawMaterials.find(
          (r) => r.id === rm.rawMaterialId,
        );

        return (
          <div key={rm.rawMaterialId} className="flex gap-3 items-center">
            <span className="w-40">{material?.name}</span>

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

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg">
          {error}
        </div>
      )}

      <button
        className="bg-blue-600 disabled:bg-gray-400 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition"
        type="submit"
      >
        {initialData ? "Update Product" : "Create Product"}
      </button>
    </form>
  );
};

export default ProductForm;