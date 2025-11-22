<script lang="ts">
	import { onMount } from 'svelte';

	let selectedGuild = '123456789'; // Default guild for demo
	let puzzle: any = null;
	let opening: any = null;
	let loading = true;

	onMount(async () => {
		try {
			const [puzzleRes, openingRes] = await Promise.all([
				fetch(`/api/guilds/${selectedGuild}/chess/puzzle`),
				fetch(`/api/guilds/${selectedGuild}/chess/opening?query=Italian Game`)
			]);

			if (puzzleRes.ok) {
				puzzle = await puzzleRes.json();
			}

			if (openingRes.ok) {
				opening = await openingRes.json();
			}
		} catch (error) {
			console.error('Failed to load chess data:', error);
		} finally {
			loading = false;
		}
	});
</script>

<div class="max-w-7xl mx-auto">
	<div class="mb-8">
		<h1 class="text-4xl font-bold bg-gradient-to-r from-cyber-blue to-cyber-purple bg-clip-text text-transparent">
			Chess Features
		</h1>
		<p class="text-gray-400 mt-2">Manage chess utilities and puzzles</p>
	</div>

	{#if loading}
		<div class="text-center py-12">
			<div class="inline-block w-8 h-8 border-4 border-cyber-blue border-t-transparent rounded-full animate-spin"></div>
			<p class="text-gray-400 mt-4">Loading chess features...</p>
		</div>
	{:else}
		<div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
			<!-- Daily Puzzle -->
			<div class="bg-gradient-to-br from-gray-900 to-gray-800 rounded-xl border border-gray-700 p-6">
				<h2 class="text-2xl font-bold mb-4">Daily Puzzle</h2>

				{#if puzzle}
					<div class="space-y-4">
						<div class="aspect-square bg-gray-800 rounded-lg flex items-center justify-center">
							<div class="text-6xl">♟️</div>
						</div>

						<div>
							<p class="text-sm text-gray-400">Puzzle ID</p>
							<p class="text-lg font-mono">{puzzle.id}</p>
						</div>

						<div>
							<p class="text-sm text-gray-400">Rating</p>
							<div class="flex items-center gap-2">
								<p class="text-lg font-bold text-cyber-blue">{puzzle.rating}</p>
								<span class="text-xs text-gray-500">ELO</span>
							</div>
						</div>

						<div>
							<p class="text-sm text-gray-400">Solution Moves</p>
							<div class="flex flex-wrap gap-2 mt-2">
								{#each puzzle.moves as move}
									<span class="px-3 py-1 bg-gray-700 rounded-lg text-sm font-mono">{move}</span>
								{/each}
							</div>
						</div>

						{#if puzzle.url}
							<a
								href={puzzle.url}
								target="_blank"
								rel="noopener noreferrer"
								class="block w-full py-3 bg-gradient-to-r from-cyber-blue to-cyber-purple rounded-lg text-center font-semibold hover:shadow-lg hover:shadow-cyber-blue/50 transition-all"
							>
								View on Lichess →
							</a>
						{/if}
					</div>
				{:else}
					<div class="text-center py-12">
						<p class="text-gray-400">No puzzle available</p>
					</div>
				{/if}
			</div>

			<!-- Opening Lookup -->
			<div class="bg-gradient-to-br from-gray-900 to-gray-800 rounded-xl border border-gray-700 p-6">
				<h2 class="text-2xl font-bold mb-4">Opening Database</h2>

				{#if opening}
					<div class="space-y-4">
						<div>
							<p class="text-sm text-gray-400">Opening Name</p>
							<p class="text-2xl font-bold text-cyber-purple">{opening.name}</p>
						</div>

						<div>
							<p class="text-sm text-gray-400">ECO Code</p>
							<p class="text-lg font-mono">{opening.eco}</p>
						</div>

						<div>
							<p class="text-sm text-gray-400">Moves</p>
							<p class="text-lg font-mono bg-gray-800 p-4 rounded-lg">{opening.moves}</p>
						</div>

						{#if opening.description}
							<div>
								<p class="text-sm text-gray-400">Description</p>
								<p class="text-gray-300">{opening.description}</p>
							</div>
						{/if}

						<div class="pt-4">
							<input
								type="text"
								placeholder="Search for an opening..."
								class="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:border-cyber-blue focus:outline-none transition-colors"
							/>
						</div>
					</div>
				{:else}
					<div class="text-center py-12">
						<p class="text-gray-400">No opening data available</p>
					</div>
				{/if}
			</div>
		</div>

		<!-- Settings Card -->
		<div class="mt-8 bg-gradient-to-br from-gray-900 to-gray-800 rounded-xl border border-gray-700 p-6">
			<h2 class="text-2xl font-bold mb-6">Chess Settings</h2>

			<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
				<div>
					<label class="block text-sm text-gray-400 mb-2">Enable Chess Features</label>
					<div class="flex items-center gap-3">
						<button class="w-12 h-6 bg-cyber-blue rounded-full relative">
							<div class="absolute right-1 top-1 w-4 h-4 bg-white rounded-full"></div>
						</button>
						<span class="text-sm">Enabled</span>
					</div>
				</div>

				<div>
					<label class="block text-sm text-gray-400 mb-2">Puzzle Difficulty</label>
					<select class="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:border-cyber-purple focus:outline-none">
						<option>Beginner (800-1200)</option>
						<option selected>Intermediate (1200-1800)</option>
						<option>Advanced (1800-2400)</option>
						<option>Master (2400+)</option>
					</select>
				</div>

				<div>
					<label class="block text-sm text-gray-400 mb-2">Daily Puzzle Channel</label>
					<input
						type="text"
						placeholder="#chess-puzzles"
						class="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:border-cyber-purple focus:outline-none"
					/>
				</div>

				<div>
					<label class="block text-sm text-gray-400 mb-2">Puzzle Notification Time</label>
					<input
						type="time"
						value="09:00"
						class="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:border-cyber-purple focus:outline-none"
					/>
				</div>
			</div>

			<div class="mt-6 flex justify-end">
				<button class="px-6 py-3 bg-gradient-to-r from-cyber-blue to-cyber-purple rounded-lg font-semibold hover:shadow-lg hover:shadow-cyber-blue/50 transition-all">
					Save Settings
				</button>
			</div>
		</div>
	{/if}
</div>
