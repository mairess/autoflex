import React, { useState } from "react";

import { useAppDispatch } from "../../store/hooks";
import { createRawMaterial, updateRawMaterial } from "../../store/slices/rawMaterialSlice";
import type { RawMaterialCreationType, RawMaterialResponseType } from "../../types/rawMaterial";

interface RawMaterialFormProps {
  initialData?: RawMaterialResponseType;
  onFinish?: () => void;
}

const RawMaterialForm: React.FC<RawMaterialFormProps> = ({
  initialData,
  onFinish,
}) => {
  const dispatch = useAppDispatch();
  const [error, setError] = useState<string | null>(null);

  const [form, setForm] = useState<RawMaterialCreationType>(() => ({
    code: initialData?.code ?? "",
    name: initialData?.name ?? "",
    stockQuantity: initialData?.stockQuantity ?? 0,
  }));

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

    if (initialData) {
      dispatch(updateRawMaterial({ id: initialData.id, data: form }));
    } else {
      dispatch(createRawMaterial(form));
    }

    onFinish?.();

    setForm({
      code: "",
      name: "",
      stockQuantity: 0,
    });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 bg-gray-50 p-4 rounded-lg border">
      <input
        placeholder="Code"
        value={form.code}
        onChange={(e) => setForm({ ...form, code: e.target.value })}
        className="w-full border rounded-lg px-3 py-2"
      />

      <input
        placeholder="Name"
        value={form.name}
        onChange={(e) => setForm({ ...form, name: e.target.value })}
        className="w-full border rounded-lg px-3 py-2"
      />

      <input
        type="number"
        min={0}
        placeholder="Stock Quantity"
        value={form.stockQuantity}
        onChange={(e) =>
          setForm({
            ...form,
            stockQuantity: Math.max(0, Number(e.target.value)),
          })
        }
        className="w-full border rounded-lg px-3 py-2"
      />

      {error && (
        <div className="bg-red-100 text-red-700 px-4 py-2 rounded">
          {error}
        </div>
      )}

      <button
        type="submit"
        className="bg-blue-600 text-white px-4 py-2 rounded-lg"
      >
        {initialData ? "Update Raw Material" : "Create Raw Material"}
      </button>
    </form>
  );
};

export default RawMaterialForm;