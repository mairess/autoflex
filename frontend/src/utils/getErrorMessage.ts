import axios from "axios";

export function getErrorMessage(error: unknown): string {
  if (axios.isAxiosError(error)) {
    return (
      error.response?.data?.message ||
      error.response?.data ||
      error.message
    );
  }

  if (error instanceof Error) {
    return error.message;
  }

  return "Unexpected error";
}
