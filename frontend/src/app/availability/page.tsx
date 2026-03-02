"use client";

import { useState } from "react";
import {
  VStack,
  Alert,
  AlertIcon,
  Heading,
  AlertTitle,
  AlertDescription,
} from "@chakra-ui/react";
import { AvailabilityForm } from "./_components/AvailabilityForm";
import { AvailabilityTable } from "./_components/AvailabilityTable";
import {
  fetchAvailability,
  AvailabilityResponse,
} from "@/shared/lib/api/availability";

export default function AvailabilityPage() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<{ title: string; message: string } | null>(
    null,
  );
  const [results, setResults] = useState<AvailabilityResponse[]>([]);

  const handleSearch = async (fromDate: string, toDate: string) => {
    setLoading(true);
    setError(null);
    setResults([]);

    try {
      const sortedData = await fetchAvailability(fromDate, toDate);
      setResults(sortedData);
    } catch (err: any) {
      setError({
        title: "Search Failed",
        message:
          err.message ||
          "An unexpected error occurred while checking availability.",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <VStack spacing={10} align="stretch" py={4}>
      <Heading size="xl" textAlign="center">
        Check Availability
      </Heading>

      <AvailabilityForm onSearch={handleSearch} isLoading={loading} />

      {error && (
        <Alert
          status="error"
          variant="subtle"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          textAlign="center"
          borderRadius="md"
          py={6}
        >
          <AlertIcon boxSize="40px" mr={0} />
          <AlertTitle mt={4} mb={1} fontSize="lg">
            {error.title}
          </AlertTitle>
          <AlertDescription maxWidth="sm">{error.message}</AlertDescription>
        </Alert>
      )}

      <AvailabilityTable results={results} isLoading={loading} />
    </VStack>
  );
}
