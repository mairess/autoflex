import React, { useState } from "react";
import { toast } from "sonner";

import { useAppDispatch } from "../../store/hooks";
import { createRawMaterial, updateRawMaterial } from "../../store/slices/rawMaterialSlice";
import type { RawMaterialCreationType, RawMaterialResponseType } from "../../types/rawMaterial";

interface RawMaterialFormProps {
  initialData?: RawMaterialResponseType;
  onFinish?: () => void;
}

function RawMaterialForm ({ initialData, onFinish }: RawMaterialFormProps) {
  const dispatch = useAppDispatch();

  const [form, setForm] = useState<RawMaterialCreationType>(() => ({
    code: initialData?.code ?? "",
    name: initialData?.name ?? "",
    stockQuantity: initialData?.stockQuantity ?? 0,
  }));

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!form.code.trim()) {
      toast.error("Code is required");
      return;
    }

    if (!form.name.trim()) {
      toast.error("Name is required. Min. 3 characters");
      return;
    }

    if (form.stockQuantity < 0) {
      toast.error("Stock quantity cannot be negative");
      return;
    }

    try {
      if (initialData) {
        await dispatch(
          updateRawMaterial({ id: initialData.id, data: form }),
        ).unwrap();

        toast.success("Raw material updated successfully!");
      } else {
        await dispatch(createRawMaterial(form)).unwrap();
        toast.success("Raw material created successfully!");
      }

      onFinish?.();

      setForm({ code: "", name: "", stockQuantity: 0 });

    } catch (err) {
      toast.error(String(err));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 bg-gray-50 p-4 rounded-lg border">

      {initialData && (
        <div className="bg-yellow-50 border border-yellow-300 text-yellow-800 px-4 py-3 rounded-lg">
    Editing raw material:
          <span className="font-semibold ml-2">
            {initialData.name}
          </span>
        </div>
      )}

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

      <div className="flex gap-3">
        <button
          type="submit"
          className="bg-blue-600 disabled:bg-gray-400 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition cursor-pointer"
        >
          {initialData ? "Update Raw Material" : "Create Raw Material"}
        </button>

        {initialData && (
          <button
            type="button"
            onClick={() => {
              setForm({ code: "", name: "", stockQuantity: 0 });
              onFinish?.();
            }}
            className="bg-gray-200 hover:bg-gray-300 text-gray-800 px-4 py-2 rounded-lg transition border cursor-pointer"
          >
      Cancel
          </button>
        )}
      </div>
    </form>
  );
};

export default RawMaterialForm;