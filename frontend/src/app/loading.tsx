"use client";

import { Skeleton, SkeletonText, Stack, Box } from "@chakra-ui/react";

export default function Loading() {
  return (
    <Stack spacing={8}>
      <Box>
        <Skeleton height="40px" width="200px" mb={4} />
        <SkeletonText noOfLines={4} spacing="4" skeletonHeight="20px" />
      </Box>
      <Box mt={10}>
        <Skeleton height="300px" borderRadius="md" />
      </Box>
    </Stack>
  );
}
