export type AvailabilityResponse = {
  carId: string;
  model: string;
  isAvailable: boolean;
  estimatedPrice?: number;
  conflicts?: boolean;
};

const BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";

export async function fetchAvailability(
  fromDate: string,
  toDate: string,
): Promise<AvailabilityResponse[]> {
  const res = await fetch(
    `${BASE_URL}/availability?from=${fromDate}&to=${toDate}`,
  );

  if (!res.ok) {
    let errorData;
    try {
      errorData = await res.json();
    } catch {
      errorData = {};
    }
    throw new Error(errorData.message || `HTTP error ${res.status}`);
  }

  const data: AvailabilityResponse[] = await res.json();

  return data.sort((a, b) => {
    if (a.isAvailable !== b.isAvailable) {
      return a.isAvailable ? -1 : 1;
    }
    return a.model.localeCompare(b.model);
  });
}
