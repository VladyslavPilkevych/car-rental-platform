"use client";

import { VStack, Heading, Text, Button } from "@chakra-ui/react";
import { useRouter } from "next/navigation";

export default function HomePage() {
  const router = useRouter();

  return (
    <VStack
      spacing={6}
      align="center"
      justify="center"
      minH="50vh"
      textAlign="center"
    >
      <Heading size="2xl">Welcome to Car Rental Platform</Heading>
      <Text fontSize="xl" color="gray.600">
        Find the perfect car for your journey. Fast, reliable, and affordable.
      </Text>
      <Button
        colorScheme="blue"
        size="lg"
        onClick={() => router.push("/availability")}
      >
        Check availability
      </Button>
    </VStack>
  );
}
